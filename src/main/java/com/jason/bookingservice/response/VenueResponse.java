package com.jason.bookingservice.response;

public record VenueResponse(
        Long id,
        String name,
        String address,
        Long totalCapacity
) {
}
