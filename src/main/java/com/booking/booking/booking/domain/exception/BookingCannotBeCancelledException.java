package com.booking.booking.booking.domain.exception;

public class BookingCannotBeCancelledException extends BookingException {
    public BookingCannotBeCancelledException() { super("Booking cannot be cancelled in its current state or dates"); }
}
