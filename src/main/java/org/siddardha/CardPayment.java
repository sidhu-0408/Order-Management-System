package org.siddardha;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CardPayment implements Payment {

    private final String cardNumber;

    public CardPayment (String cardNumber) {
        if(cardNumber == null || cardNumber.length() < 16) {
            throw new IllegalArgumentException("account number should be 16 digits");
        }
        this.cardNumber = cardNumber;
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {

        if(amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("amount should not be negative");

        String transactionId = UUID.randomUUID().toString();
        boolean declined = cardNumber.endsWith("0000");
        PaymentStatus status = declined ? PaymentStatus.FAILED : PaymentStatus.SUCCESS;
        String lastDigits = cardNumber.substring(12);
        String message = declined ? "payment declined" : "payment success for card" + lastDigits;
        return new PaymentResult(transactionId,PaymentMethod.CARD,status,amount, Instant.now(),message);
    }

}
