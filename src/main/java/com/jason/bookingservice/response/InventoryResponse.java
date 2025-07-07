package com.jason.bookingservice.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InventoryResponse(
        Long eventId,
        String event,
        Long capacity,
        VenueResponse venue,
        BigDecimal ticketPrice
) {
}
