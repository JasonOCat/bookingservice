package com.jason.bookingservice.response;

import lombok.Builder;

@Builder
public record BookingResponse(
    Long bookingId,
    Long userid,
    Long eventId,
    Long ticketCount,
    String ticketPrice
) {
}
