package com.booking.booking.common.exception;

public class AccessDeniedException extends BookNowException {
    public AccessDeniedException(String message) {
        super("ACCESS_DENIED", message);
    }
}
