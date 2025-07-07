package com.jason.bookingservice.request;

import lombok.Builder;

@Builder
public record BookingRequest(
    Long userId,
    Long eventId,
    Long ticketCount
) {
}
