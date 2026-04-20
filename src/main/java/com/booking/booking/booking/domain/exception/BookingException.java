package com.booking.booking.booking.domain.exception;

public abstract class BookingException extends RuntimeException {
    protected BookingException(String message) { super(message); }
}

