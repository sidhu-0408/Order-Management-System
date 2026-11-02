package org.siddardha;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void happyPathTransitionsAreAllowed() {
        assertTrue(OrderStatus.CREATED.canTransitionTo(OrderStatus.PAID));
        assertTrue(OrderStatus.PAID.canTransitionTo(OrderStatus.PROCESSING));
        assertTrue(OrderStatus.PROCESSING.canTransitionTo(OrderStatus.SHIPPED));
        assertTrue(OrderStatus.SHIPPED.canTransitionTo(OrderStatus.DELIVERED));
    }

    @Test
    void cancelOnlyFromCreatedOrPaid() {
        assertTrue(OrderStatus.CREATED.canTransitionTo(OrderStatus.CANCELLED));
        assertTrue(OrderStatus.PAID.canTransitionTo(OrderStatus.CANCELLED));
        assertFalse(OrderStatus.PROCESSING.canTransitionTo(OrderStatus.CANCELLED));
        assertFalse(OrderStatus.SHIPPED.canTransitionTo(OrderStatus.CANCELLED));
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(OrderStatus.CANCELLED));
    }

    @Test
    void skippingAStepIsRejected() {
        assertFalse(OrderStatus.CREATED.canTransitionTo(OrderStatus.SHIPPED));
        assertFalse(OrderStatus.PAID.canTransitionTo(OrderStatus.DELIVERED));
    }

    @Test
    void goingBackwardsIsRejected() {
        assertFalse(OrderStatus.PAID.canTransitionTo(OrderStatus.CREATED));
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(OrderStatus.SHIPPED));
    }

    // Runs once per enum value: final states can't move anywhere.
    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void deliveredAndCancelledAreFinal(OrderStatus next) {
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(next));
        assertFalse(OrderStatus.CANCELLED.canTransitionTo(next));
    }

    @Test
    void nullIsNeverAllowed() {
        for (OrderStatus status : OrderStatus.values()) {
            assertFalse(status.canTransitionTo(null), status + " -> null");
        }
    }
}
