package com.jason.bookingservice.event;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BookingEvent(
        Long userId,
        Long eventId,
        Long ticketCount,
        BigDecimal totalPrice
) {
}
