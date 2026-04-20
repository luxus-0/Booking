package com.booking.booking.booking.domain.vo;

import java.time.Instant;

public record Audit(Instant createdAt, Instant updatedAt) {
    public static Audit create() {
        Instant now = Instant.now();
        return new Audit(now, now);
    }

    public Audit update() {
        return new Audit(this.createdAt, Instant.now());
    }

}
