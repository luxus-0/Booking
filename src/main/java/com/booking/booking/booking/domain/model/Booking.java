package com.booking.booking.booking.domain.model;

import com.booking.booking.booking.domain.exception.BookingCannotBeCancelledException;
import com.booking.booking.booking.domain.exception.InvalidBookingStatusException;
import com.booking.booking.common.vo.Money;
import com.booking.booking.booking.domain.vo.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Booking {

    private final BookingId id;
    private final UserId userId;
    private final PropertyId propertyId;
    private final RoomId roomId;

    private BookingPeriod period;
    private GuestCount guestsCount;
    private Money money;
    private Status status;

    private Instant expiresAt;
    private CancellationDetails cancellation;

    private Audit audit;

    public void confirm() {
        if (this.status != Status.PENDING) {
            throw new InvalidBookingStatusException("Only PENDING bookings can be confirmed");
        }
        this.status = Status.CONFIRMED;
        this.audit = audit.update();
    }

    public boolean isCancellable() {
        return status == Status.CONFIRMED && period.checkIn().isAfter(LocalDate.now());
    }

    public void cancel(String reason) {
        if (!isCancellable()) {
            throw new BookingCannotBeCancelledException();
        }
        this.status = Status.CANCELLED;
        this.cancellation = new CancellationDetails(Instant.now(), reason);
        this.audit = audit.update();
    }

    public void changePeriod(BookingPeriod newPeriod) {
        if (this.status != Status.PENDING) {
            throw new InvalidBookingStatusException("Dates can only be changed for PENDING bookings");
        }
        this.period = newPeriod;
        this.audit = audit.update();
    }

    public void updateGuests(GuestCount newCount) {
        if (this.status != Status.PENDING && this.status != Status.CONFIRMED) {
            throw new InvalidBookingStatusException("Guest count can only be changed for PENDING or CONFIRMED bookings");
        }
        this.guestsCount = newCount;
        this.audit = audit.update();
    }
}