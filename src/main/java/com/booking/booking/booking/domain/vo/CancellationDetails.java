package com.booking.booking.booking.domain.vo;

import java.time.Instant;

public record CancellationDetails(Instant cancelledAt, String reason) {
}
