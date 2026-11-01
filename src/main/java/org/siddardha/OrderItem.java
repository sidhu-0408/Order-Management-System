package org.siddardha;

import java.math.BigDecimal;

public record OrderItem(
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
    public OrderItem {
        if (quantity == null || quantity <=0) throw new IllegalArgumentException("quantity should not be less than 0");
        if (productId == null) throw new IllegalArgumentException("productId required");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("unitPrice must not be negative");
    }

    public BigDecimal subTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
