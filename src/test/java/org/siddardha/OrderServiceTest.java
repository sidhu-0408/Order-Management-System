package org.siddardha;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private Inventory inventory;
    private OrderService service;
    private Product pen;
    private Product diary;

    // Runs before EVERY test, so each test starts from the same fresh state.
    @BeforeEach
    void setUp() {
        pen = new Product("Pen", new BigDecimal("10.50"));
        diary = new Product("Diary", new BigDecimal("45.00"));
        inventory = new Inventory();
        inventory.addStock(pen.getId(), 10);
        inventory.addStock(diary.getId(), 5);
        service = new OrderService(inventory, Map.of(pen.getId(), pen, diary.getId(), diary));
    }

    // A Payment written as a lambda - handy fake for tests (no real card needed).
    private static Payment fixed(PaymentStatus status) {
        return amount -> new PaymentResult("TX-TEST", PaymentMethod.CARD, status, amount, Instant.now(), "test");
    }

    private static void assertMoney(String expected, BigDecimal actual) {
        assertEquals(0, new BigDecimal(expected).compareTo(actual), "expected " + expected + " but was " + actual);
    }

    // ---------- placeOrder ----------

    @Test
    void placeOrderDeductsStockAndStartsCreated() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 2, diary.getId(), 1));

        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertMoney("66.00", order.getTotal());
        assertEquals(8, inventory.getStock(pen.getId()));
        assertEquals(4, inventory.getStock(diary.getId()));
    }

    @Test
    void insufficientStockThrowsAndChangesNothing() {
        assertThrows(InsufficientStockException.class,
                () -> service.placeOrder(1, Map.of(pen.getId(), 1, diary.getId(), 100)));

        // all-or-nothing: the pen line was valid but must NOT have been deducted
        assertEquals(10, inventory.getStock(pen.getId()));
        assertEquals(5, inventory.getStock(diary.getId()));
        assertTrue(service.getOrders().isEmpty());
    }

    @Test
    void unknownOrInactiveProductIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(1, Map.of(999, 1)));
        diary.deactivate();
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(1, Map.of(diary.getId(), 1)));
    }

    @Test
    void zeroQuantityOrEmptyRequestIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(1, Map.of(pen.getId(), 0)));
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(1, Map.of()));
    }

    // ---------- pay ----------

    @Test
    void successfulPaymentMovesOrderToPaid() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 2));

        PaymentResult result = service.pay(order.getId(), new CardPayment("4111111111111111"));

        assertEquals(PaymentStatus.SUCCESS, result.status());
        assertMoney("21.00", result.amount());   // charged exactly the order total
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void declinedPaymentKeepsOrderCreated() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 1));

        PaymentResult result = service.pay(order.getId(), new CardPayment("4111111111110000"));

        assertEquals(PaymentStatus.FAILED, result.status());
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    void anyPaymentImplementationWorks() throws Exception {
        Order byUpi = service.placeOrder(1, Map.of(pen.getId(), 1));
        Order byLambda = service.placeOrder(2, Map.of(pen.getId(), 1));

        service.pay(byUpi.getId(), new UpiPayment("asha@okbank"));
        service.pay(byLambda.getId(), fixed(PaymentStatus.SUCCESS));

        assertEquals(OrderStatus.PAID, byUpi.getStatus());
        assertEquals(OrderStatus.PAID, byLambda.getStatus());
    }

    @Test
    void cannotPayTwice() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 1));
        service.pay(order.getId(), fixed(PaymentStatus.SUCCESS));

        assertThrows(InvalidTransitionException.class, () -> service.pay(order.getId(), fixed(PaymentStatus.SUCCESS)));
    }

    @Test
    void cancelledOrderIsNotChargedAtAll() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 1));
        order.changeStatus(OrderStatus.CANCELLED);

        boolean[] charged = {false};
        Payment spy = amount -> {
            charged[0] = true;
            return new PaymentResult("TX", PaymentMethod.CARD, PaymentStatus.SUCCESS, amount, Instant.now(), "");
        };

        assertThrows(InvalidTransitionException.class, () -> service.pay(order.getId(), spy));
        assertFalse(charged[0], "payment must not be processed for a cancelled order");
    }

    @Test
    void payUnknownOrderOrNullPaymentIsRejected() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 1));
        assertThrows(IllegalArgumentException.class, () -> service.pay(999, fixed(PaymentStatus.SUCCESS)));
        assertThrows(IllegalArgumentException.class, () -> service.pay(order.getId(), null));
    }

    // ---------- streams (Day 5) ----------

    @Test
    void revenueCountsOnlyPaidOnwardOrders() throws Exception {
        Order paid = service.placeOrder(1, Map.of(pen.getId(), 2, diary.getId(), 1));
        service.pay(paid.getId(), fixed(PaymentStatus.SUCCESS));
        service.placeOrder(2, Map.of(pen.getId(), 1));                         // CREATED: excluded
        Order cancelled = service.placeOrder(3, Map.of(diary.getId(), 1));
        cancelled.changeStatus(OrderStatus.CANCELLED);                          // CANCELLED: excluded

        Map<Integer, BigDecimal> revenue = service.revenueByProduct();

        assertEquals(2, revenue.size());
        assertMoney("21.00", revenue.get(pen.getId()));
        assertMoney("45.00", revenue.get(diary.getId()));
    }

    @Test
    void countAndGroupByStatus() throws Exception {
        Order a = service.placeOrder(1, Map.of(pen.getId(), 1));
        service.placeOrder(2, Map.of(pen.getId(), 1));
        service.pay(a.getId(), fixed(PaymentStatus.SUCCESS));

        assertEquals(Map.of(OrderStatus.CREATED, 1L, OrderStatus.PAID, 1L), service.countByStatus());
        assertEquals(1, service.ordersByStatus().get(OrderStatus.PAID).size());
        assertNull(service.ordersByStatus().get(OrderStatus.SHIPPED));   // groupingBy has no empty groups
    }

    @Test
    void findById() throws Exception {
        Order order = service.placeOrder(1, Map.of(pen.getId(), 1));

        assertEquals(order, service.findById(order.getId()).orElseThrow());
        assertTrue(service.findById(-1).isEmpty());
    }

    @Test
    void getOrdersIsReadOnly() throws Exception {
        service.placeOrder(1, Map.of(pen.getId(), 1));
        assertThrows(UnsupportedOperationException.class, () -> service.getOrders().clear());
        assertEquals(1, service.getOrders().size());
    }
}
