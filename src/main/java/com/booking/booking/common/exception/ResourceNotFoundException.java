package com.booking.booking.common.exception;

public class ResourceNotFoundException extends BookNowException {
    public ResourceNotFoundException(String resource, Object id) {
        super("NOT_FOUND", resource + " not found: " + id);
    }
}
