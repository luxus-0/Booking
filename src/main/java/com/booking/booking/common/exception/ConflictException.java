package com.booking.booking.common.exception;

public class ConflictException extends BookNowException {
    public ConflictException(String message) {
        super("CONFLICT", message);
    }
}
