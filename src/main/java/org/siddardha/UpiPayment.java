package org.siddardha;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class UpiPayment implements Payment {

    private final String upiId;

    public UpiPayment (String upiId) {
        boolean isValid = validateEmail(upiId);
        if(!isValid)
            throw new IllegalArgumentException("email is invalid");
        this.upiId = upiId;
    }

    private boolean validateEmail(String email) {
        if(email ==  null)
            return false;
        int firstAt = email.indexOf('@');
        int secondAt = email.lastIndexOf('@');

        return firstAt > 0 && firstAt == secondAt && firstAt < email.length() -1;
    }

    @Override
    public PaymentResult processPayment(BigDecimal amount) {
        if(amount == null || amount.signum() <= 0)
            throw new IllegalArgumentException("amount should not be negative");

        String transactionId = UUID.randomUUID().toString();
        boolean decline = upiId.startsWith("fail");
        PaymentStatus status = decline ? PaymentStatus.FAILED : PaymentStatus.SUCCESS;
        String message = decline ? "Payment Failed!!" : "Payment Success";
        return new PaymentResult(transactionId, PaymentMethod.UPI,status,amount, Instant.now(),message);
    }

}
