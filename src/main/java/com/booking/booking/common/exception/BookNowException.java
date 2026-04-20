package com.booking.booking.common.exception;

import lombok.Getter;

@Getter
public abstract class BookNowException extends RuntimeException {
    private final String code;

    protected BookNowException(String code, String message) {
        super(message);
        this.code = code;
    }

}
