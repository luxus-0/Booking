package com.booking.booking.booking.domain.model;

import com.booking.booking.booking.domain.event.BookingCancelled;
import com.booking.booking.booking.domain.event.BookingConfirmed;
import com.booking.booking.booking.domain.event.BookingCreated;
import com.booking.booking.booking.domain.exception.BookingCannotBeCancelledException;
import com.booking.booking.booking.domain.exception.InvalidBookingStatusException;
import com.booking.booking.booking.domain.vo.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Booking {

    private final BookingId id;
    private final UserId userId;
    private final PropertyId propertyId;
    private final RoomId roomId;

    private final List<Object> domainEvents = new ArrayList<>();


    private DateRange period;
    private GuestCount guestsCount;
    private Money money;
    private Status status;

    private Instant expiresAt;
    private CancellationDetails cancellation;

    private Audit audit;

    public static Booking create(
            BookingId id,
            UserId userId,
            PropertyId propertyId,
            RoomId roomId,
            DateRange period,
            GuestCount guestsCount,
            Money money,
            Instant expiresAt
    ) {
        Booking booking = Booking.builder()
                .id(id)
                .userId(userId)
                .propertyId(propertyId)
                .roomId(roomId)
                .period(period)
                .guestsCount(guestsCount)
                .money(money)
                .expiresAt(expiresAt)
                .status(Status.PENDING)
                .audit(Audit.create())
                .build();

        booking.domainEvents.add(new BookingCreated(
                id,
                userId,
                money,
                period,
                Instant.now(),
                Instant.now()
        ));

        return booking;
    }

    public void confirm() {
        if (this.status != Status.PENDING) {
            throw new InvalidBookingStatusException("Only PENDING bookings can be confirmed");
        }
        this.status = Status.CONFIRMED;
        this.audit = audit.update();

        domainEvents.add(new BookingConfirmed(
                this.id,
                this.userId,
                this.money,
                Instant.now()
        ));
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

        domainEvents.add(new BookingCancelled(
                this.id,
                reason,
                Instant.now()
        ));
    }

    public void changePeriod(DateRange newPeriod) {
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