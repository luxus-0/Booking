package com.booking.booking.booking.domain.model;

import com.booking.booking.booking.domain.exception.InvalidBookingDatesException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record DateRange(LocalDate checkIn, LocalDate checkOut) {
    public DateRange {
        if (checkIn == null || checkOut == null) {
            throw new InvalidBookingDatesException("Dates cannot be null");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidBookingDatesException("Check-out date must be after check-in date");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidBookingDatesException("Booking cannot start in the past");
        }
    }

    public long numberOfNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public boolean includes(LocalDate date) {
        return !date.isBefore(checkIn) && date.isBefore(checkOut);
    }

    @Override
    public String toString() {
        return "DateRange[" +
                "checkIn=" + checkIn + ", " +
                "checkOut=" + checkOut + ']';
    }

}
