package com.booking.booking.common.vo;

import com.booking.booking.common.exception.InvalidMoneyException;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, String currency) {
    public Money {
        if (amount == null) {
            throw new InvalidMoneyException("Amount cannot be null");
        }
        if (currency == null) {
            throw new InvalidMoneyException("Currency cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyException("Amount cannot be negative");
        }

        validateCurrency(currency);
    }

    private void validateCurrency(String currency) {
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new InvalidMoneyException("Invalid Currency " + currency);
        }
    }
}
