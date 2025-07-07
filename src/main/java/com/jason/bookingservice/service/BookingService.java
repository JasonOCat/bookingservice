package com.jason.bookingservice.service;

import com.jason.bookingservice.client.InventoryServiceClient;
import com.jason.bookingservice.entity.Customer;
import com.jason.bookingservice.repository.CustomerRepository;
import com.jason.bookingservice.request.BookingRequest;
import com.jason.bookingservice.response.BookingResponse;
import com.jason.bookingservice.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public BookingResponse createBooking(final BookingRequest request) {
        // check if user exists
        final Customer customer = customerRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if there is enough inventory
        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.eventId());
        System.out.println("Inventory Server Response:" + inventoryResponse);

        return BookingResponse.builder()
                .build();
    }
}
