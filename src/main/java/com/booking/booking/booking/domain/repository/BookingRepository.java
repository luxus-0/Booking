package com.booking.booking.booking.domain.repository;

import com.booking.booking.booking.domain.model.Booking;
import com.booking.booking.booking.domain.model.DateRange;
import com.booking.booking.booking.domain.vo.*;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);
    Optional<Booking> findById(BookingId id);

    List<Booking> findAllByUserId(UserId userId);
    List<Booking> findActiveByPropertyId(PropertyId propertyId);

    boolean hasConflictingBooking(RoomId roomId, DateRange dateRange);

}
