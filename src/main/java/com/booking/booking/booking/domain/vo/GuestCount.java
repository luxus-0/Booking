package com.booking.booking.booking.domain.vo;

import com.booking.booking.booking.domain.exception.InvalidGuestCountException;

public record GuestCount(Integer count) {
    public GuestCount {
        if (count == null) {
            throw new InvalidGuestCountException("Guest count cannot be null");
        }

        if (count <= 0) {
            throw new InvalidGuestCountException("Guest count must be greater than zero");
        }

        if (count > 50) {
            throw new InvalidGuestCountException("Guest count exceeds maximum limit for a single booking");
        }
    }
}
