package com.booking.booking.booking.domain.exception;

public class InvalidGuestCountException extends RuntimeException {
    public InvalidGuestCountException(String message) {
        super(message);
    }
}
