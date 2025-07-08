package com.jason.bookingservice.service;

import com.jason.bookingservice.client.InventoryServiceClient;
import com.jason.bookingservice.entity.Customer;
import com.jason.bookingservice.event.BookingEvent;
import com.jason.bookingservice.repository.CustomerRepository;
import com.jason.bookingservice.request.BookingRequest;
import com.jason.bookingservice.response.BookingResponse;
import com.jason.bookingservice.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.eventId());
        log.info("Inventory Server Response: {}", inventoryResponse);
        if (inventoryResponse.capacity() < request.ticketCount()) {
            throw new RuntimeException("Not enough inventory");
        }

        // create booking
        final BookingEvent bookingEvent = createBookingEvent(request, customer, inventoryResponse);

        //send booking to Order Service on a Kafka Topic
        kafkaTemplate.send("booking", bookingEvent);
        log.info("Booking sent to Kafka: {}", bookingEvent);

        return BookingResponse.builder()
                .userid(bookingEvent.userId())
                .eventId(bookingEvent.eventId())
                .ticketCount(bookingEvent.ticketCount())
                .totalPrice(bookingEvent.totalPrice())
                .build();
    }

    private BookingEvent createBookingEvent(
            final BookingRequest request,
            final Customer customer,
            final InventoryResponse inventoryResponse
    ) {
        return BookingEvent.builder()
                .userId(customer.getId())
                .eventId(request.eventId())
                .ticketCount(request.ticketCount())
                .totalPrice(inventoryResponse.ticketPrice().multiply(BigDecimal.valueOf(request.ticketCount())))
                .build();
    }
}
