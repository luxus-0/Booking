package com.booking.booking.booking.domain.event;

import com.booking.booking.booking.domain.model.Money;
import com.booking.booking.booking.domain.vo.*;

import java.time.Instant;

public record BookingConfirmed(
        BookingId bookingId,
        UserId userId,
        Money amount,
        Instant occurredAt
) {}
