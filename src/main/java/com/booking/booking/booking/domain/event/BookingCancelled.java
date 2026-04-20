package com.booking.booking.booking.domain.event;

import com.booking.booking.booking.domain.vo.BookingId;

import java.time.Instant;

public record BookingCancelled(
        BookingId bookingId,
        String reason,
        Instant cancelledAt
) {}
