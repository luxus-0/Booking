package com.booking.booking.common.config;

import com.booking.booking.booking.domain.exception.BookingException;
import com.booking.booking.booking.domain.exception.InvalidBookingDatesException;
import com.booking.booking.booking.domain.exception.InvalidGuestCountException;
import com.booking.booking.booking.domain.exception.RoomNotAvailableException;
import com.booking.booking.common.exception.AccessDeniedException;
import com.booking.booking.common.exception.ConflictException;
import com.booking.booking.common.exception.InvalidMoneyException;
import com.booking.booking.common.exception.ResourceNotFoundException;
import com.booking.booking.common.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiError.of(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, List<String>> details = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = error instanceof FieldError fe ? fe.getField() : error.getObjectName();
            details.computeIfAbsent(field, k -> new java.util.ArrayList<>())
                    .add(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of("VALIDATION_ERROR", "Validation failed", details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.of("INTERNAL_ERROR", "An unexpected error occurred"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiError.of("ACCESS_DENIED", "You do not have permission to access this resource"));
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ApiError> handleDomainException(BookingException ex) {
        log.warn("Booking domain: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of("INVALID_BOOKING_DOMAIN", ex.getMessage()));
    }

    @ExceptionHandler(InvalidBookingDatesException.class)
    public ResponseEntity<ApiError> handleInvalidDates(InvalidBookingDatesException ex) {
        log.warn("Invalid booking dates: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of("INVALID_BOOKING_DATES", ex.getMessage()));
    }

    @ExceptionHandler(InvalidGuestCountException.class)
    public ResponseEntity<ApiError> handleInvalidGuestCount(InvalidGuestCountException ex) {
        log.warn("Invalid guest count: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of("INVALID_GUEST_COUNT", ex.getMessage()));
    }

    @ExceptionHandler(InvalidMoneyException.class)
    public ResponseEntity<ApiError> handleInvalidMoney(InvalidMoneyException ex) {
        log.warn("Money validation failed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of("INVALID_MONEY_FORMAT", ex.getMessage()));
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<ApiError> handleRoomNotAvailable(RoomNotAvailableException ex) {
        log.warn("Room availability conflict: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiError.of("ROOM_NOT_AVAILABLE", ex.getMessage()));
    }
}
