package org.siddardha;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static OrderItem item(int productId, int qty, String price) {
        return new OrderItem(productId, "P" + productId, qty, new BigDecimal(price));
    }

    @Test
    void totalIsSumOfQuantityTimesUnitPrice() {
        Order order = new Order(1, List.of(item(1, 2, "10.50"), item(2, 1, "45.00")));
        // compareTo, not equals: BigDecimal equals() also compares scale (66.0 vs 66.00)
        assertEquals(0, new BigDecimal("66.00").compareTo(order.getTotal()));
    }

    @Test
    void bigDecimalAvoidsDoubleRoundingErrors() {
        // With double, 0.1 * 3 = 0.30000000000000004
        Order order = new Order(1, List.of(item(1, 3, "0.10")));
        assertEquals(new BigDecimal("0.30"), order.getTotal());
    }

    @Test
    void newOrderStartsAsCreated() {
        Order order = new Order(1, List.of(item(1, 1, "5.00")));
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    void emptyOrderIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Order(1, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new Order(1, null));
    }

    @Test
    void invalidTransitionThrowsAndKeepsStatus() {
        Order order = new Order(1, List.of(item(1, 1, "5.00")));
        assertThrows(InvalidTransitionException.class, () -> order.changeStatus(OrderStatus.SHIPPED));
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    void itemsCannotBeChangedAfterCreation() {
        List<OrderItem> items = new ArrayList<>(List.of(item(1, 1, "5.00")));
        Order order = new Order(1, items);

        items.add(item(2, 1, "99.00"));   // changing the caller's list doesn't affect the order
        assertEquals(1, order.getItems().size());
        assertThrows(UnsupportedOperationException.class, () -> order.getItems().clear());
    }
}
