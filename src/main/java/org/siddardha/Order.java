package org.siddardha;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class Order {

    private static int nextId = 1;

    private final int id;
    private final int userId;
    private final List<OrderItem> items;
    private final Instant createdAt;
    private OrderStatus status = OrderStatus.CREATED;

    public Order(int userId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        this.id = nextId++;
        this.userId = userId;
        this.items = List.copyOf(items);   // immutable copy: items can't be added or removed later
        this.createdAt = Instant.now();
    }

    // Sum of quantity x unit price for every item.
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.subTotal());   // BigDecimal is immutable: add() returns a new value
        }
        return total;
    }

    // The only way to change status; bad transitions are rejected.
    public void changeStatus(OrderStatus next) {
        if (next == null || !status.canTransitionTo(next)) {
            throw new InvalidTransitionException(
                    "Cannot change order " + id + " from " + status + " to " + next);
        }
        this.status = next;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", userId=" + userId + ", items=" + items.size()
                + ", total=" + getTotal() + ", status=" + status + "}";
    }
}
