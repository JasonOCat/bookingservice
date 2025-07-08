package com.jason.bookingservice.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookingResponse(
    Long userid,
    Long eventId,
    Long ticketCount,
    BigDecimal totalPrice
) {
}
