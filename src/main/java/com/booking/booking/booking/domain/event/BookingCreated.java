package com.booking.booking.booking.domain.event;

import com.booking.booking.booking.domain.model.DateRange;
import com.booking.booking.booking.domain.model.Money;
import com.booking.booking.booking.domain.vo.*;

import java.time.Instant;

public record BookingCreated(
        BookingId bookingId,
        UserId userId,
        Money amount,
        DateRange period,
        Instant expiresAt,
        Instant occurredAt
) {}
