package org.siddardha;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResult(
        String transactionId,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        BigDecimal amount,
        Instant timeStamp,
        String message
) {

    public PaymentResult {
        if (transactionId == null || transactionId.isBlank())
            throw new IllegalArgumentException("transactionId required");
        if (amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("amount should not be negative");
    }
}
