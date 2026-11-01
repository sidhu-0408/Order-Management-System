package org.siddardha;

public enum OrderStatus {
    CREATED,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus next) {
        switch (this) {
            case CREATED :
                return next == PAID || next == CANCELLED;
            case PAID:
                return next == PROCESSING || next == CANCELLED;
            case PROCESSING:
                return next == SHIPPED;
            case SHIPPED:
                return next == DELIVERED;
            default:
                return false;
        }
    }
}
