package org.siddardha;

import java.math.BigDecimal;

public interface Payment {

    PaymentResult processPayment(BigDecimal amount);
}
