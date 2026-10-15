package org.siddardha;

import java.math.BigDecimal;

//TODO: need to add validation and other stuff related to day 4
public record OrderItem(
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {
}
