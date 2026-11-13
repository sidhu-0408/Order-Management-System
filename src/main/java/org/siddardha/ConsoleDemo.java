package org.siddardha;

import java.math.BigDecimal;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ConsoleDemo {
    public static void main(String[] args) {
        // 1. Valid objects
//        System.out.println("=== 1. Valid objects ===");
//        Product pen = new Product("Pen", new BigDecimal("10.50"));
//        pen.setCategory("Stationery");
//        Product notebook = new Product("Notebook", new BigDecimal("45.00"));
//        notebook.setCategory("Stationery");
//        Product laptop = new Product("Laptop", new BigDecimal("55999.99"));
//        laptop.setCategory("Electronics");
//
//        System.out.println(pen);
//        System.out.println(notebook);
//        System.out.println(laptop);
//
//        User asha = new User("Asha", "asha@example.com", "hash123");
//        User ravi = new User("Ravi", "ravi@example.com", "hash456");
//        System.out.println(asha);   // must NOT show the password hash
//        System.out.println(ravi);
//
//        // 2. Validation
//        System.out.println("\n=== 2. Validation ===");
//        expectRejected("blank product name", () -> new Product("", new BigDecimal("10")));
//        expectRejected("null product name", () -> new Product(null, new BigDecimal("10")));
//        expectRejected("negative price", () -> new Product("Pen", new BigDecimal("-1")));
//        expectRejected("null price", () -> new Product("Pen", null));
//        expectRejected("setPrice negative", () -> pen.setPrice(new BigDecimal("-5")));
//        expectRejected("invalid email", () -> new User("Bad", "not-an-email", "hash"));
//        expectRejected("blank user name", () -> new User(" ", "x@example.com", "hash"));
//
//        // 3. equals / hashCode
//        System.out.println("\n=== 3. equals / hashCode ===");
//        User u1 = new User("Meena", "meena@example.com", "h1");
//        User u2 = new User("Meena K", "MEENA@example.com", "h2");   // same email, different case
//        System.out.println("u1 == u2      : " + (u1 == u2));
//        System.out.println("u1.equals(u2) : " + u1.equals(u2));
//
//        Set<User> users = new HashSet<>();
//        users.add(u1);
//        users.add(u2);
//        System.out.println("Set size after adding u1, u2 : " + users.size());   // expect 1
//        users.add(asha);
//        System.out.println("Set size after adding asha   : " + users.size());   // expect 2
//
//        // 4. id and deactivate()
//        System.out.println("\n=== 4. id and deactivate ===");
//        System.out.println("pen id = " + pen.getId() + ", notebook id = " + notebook.getId());
//        System.out.println("pen active before: " + pen.isActive());
//        pen.deactivate();
//        System.out.println("pen active after : " + pen.isActive());
//        // pen.setId(99);        // uncomment: it should NOT compile
//
//        // 5. Day 4: placing orders
//        // Fresh products, because 'pen' above was deactivated.
//        System.out.println("\n=== 5. Placing orders ===");
//        Product ballPen = new Product("Ball Pen", new BigDecimal("10.50"));
//        Product diary = new Product("Diary", new BigDecimal("45.00"));
//
//        Inventory inventory = new Inventory();
//        inventory.addStock(ballPen.getId(), 10);
//        inventory.addStock(diary.getId(), 5);
//
//        Map<Integer, Product> catalog = new HashMap<>();
//        catalog.put(ballPen.getId(), ballPen);
//        catalog.put(diary.getId(), diary);
//        OrderService orderService = new OrderService(inventory, catalog);
//
//        // Check 1: valid order -> total 66.00 (2 x 10.50 + 1 x 45.00), ball pen stock 10 -> 8
//        Order order = null;
//        try {
//            order = orderService.placeOrder(1, Map.of(ballPen.getId(), 2, diary.getId(), 1));
//            System.out.println("OK, order placed: " + order);
//            System.out.println("   total = " + order.getTotal() + " (expect 66.00)");
//            System.out.println("   ball pen stock = " + inventory.getStock(ballPen.getId()) + " (expect 8)");
//            System.out.println("   diary stock    = " + inventory.getStock(diary.getId()) + " (expect 4)");
//        } catch (InsufficientStockException e) {
//            System.out.println("FAIL: valid order was rejected: " + e.getMessage());
//        }
//
//        // Check 2: not enough stock -> InsufficientStockException, and NO stock changes (all-or-nothing)
//        System.out.println("\n=== 6. Not enough stock ===");
//        try {
//            orderService.placeOrder(1, Map.of(ballPen.getId(), 1, diary.getId(), 100));
//            System.out.println("FAIL: order for 100 diaries was accepted");
//        } catch (InsufficientStockException e) {
//            System.out.println("OK, rejected: " + e.getMessage());
//        }
//        System.out.println("   ball pen stock = " + inventory.getStock(ballPen.getId()) + " (expect 8, unchanged)");
//        System.out.println("   diary stock    = " + inventory.getStock(diary.getId()) + " (expect 4, unchanged)");
//
//        // Check 3 and 4 need the order from check 1
//        if (order != null) {
//            // Check 3: valid transitions CREATED -> PAID -> PROCESSING
//            System.out.println("\n=== 7. Valid status changes ===");
//            order.changeStatus(OrderStatus.PAID);
//            System.out.println("OK, status = " + order.getStatus() + " (expect PAID)");
//            order.changeStatus(OrderStatus.PROCESSING);
//            System.out.println("OK, status = " + order.getStatus() + " (expect PROCESSING)");
//
//            // Check 4: invalid transition PROCESSING -> CANCELLED -> InvalidTransitionException
//            System.out.println("\n=== 8. Invalid status change ===");
//            try {
//                order.changeStatus(OrderStatus.CANCELLED);
//                System.out.println("FAIL: cancelled an order that is already PROCESSING");
//            } catch (InvalidTransitionException e) {
//                System.out.println("OK, rejected: " + e.getMessage());
//            }
//            System.out.println("   status = " + order.getStatus() + " (expect PROCESSING, unchanged)");
//        }
//
//        // 9. Day 5: streams - revenue per product
//        // Order #1 above is PROCESSING (counts). Add one unpaid and one cancelled order (must NOT count).
//        System.out.println("\n=== 9. Revenue per product ===");
//        try {
//            Order unpaid = orderService.placeOrder(2, Map.of(ballPen.getId(), 1));   // stays CREATED
//            Order cancelled = orderService.placeOrder(3, Map.of(diary.getId(), 1));
//            cancelled.changeStatus(OrderStatus.CANCELLED);
//            System.out.println("unpaid order    : " + unpaid);
//            System.out.println("cancelled order : " + cancelled);
//        } catch (InsufficientStockException e) {
//            System.out.println("FAIL: setup order was rejected: " + e.getMessage());
//        }
//
//        Map<Integer, BigDecimal> revenue = orderService.revenueByProduct();
//        System.out.println("revenue map = " + revenue);
//        System.out.println("   Ball Pen revenue = " + revenue.get(ballPen.getId()) + " (expect 21.00)");
//        System.out.println("   Diary revenue    = " + revenue.get(diary.getId()) + " (expect 45.00)");
//        System.out.println("   products in map  = " + revenue.size() + " (expect 2)");

        // 10. Day 5: streams - grouping, counting, Optional
//        System.out.println("\n=== 10. Orders by status ===");
//        Map<OrderStatus, List<Order>> byStatus = orderService.ordersByStatus();
//        byStatus.forEach((status, list) -> System.out.println("   " + status + " -> order ids "
//                + list.stream().map(Order::getId).toList()));
//        System.out.println("   (expect CREATED -> [2], PROCESSING -> [1], CANCELLED -> [3], in that order)");
//
//        Map<OrderStatus, Long> counts = orderService.countByStatus();
//        System.out.println("count by status = " + counts
//                + " (expect {CREATED=1, PROCESSING=1, CANCELLED=1})");
//        System.out.println("   PAID present? " + counts.containsKey(OrderStatus.PAID)
//                + " (expect false: groupingBy only creates keys that have orders)");
//
//        System.out.println("\n=== 11. findById ===");
//        Optional<Order> found = orderService.findById(1);
//        System.out.println("findById(1)   = " + found.map(Order::toString).orElse("empty")
//                + " (expect id=1, status=PROCESSING)");
//        Optional<Order> missing = orderService.findById(999);
//        System.out.println("findById(999) = " + (missing.isPresent() ? "FAIL: " + missing.get() : "empty")
//                + " (expect empty)");
//
//        // 12. Day 6: pluggable payment - OrderService.pay() only knows the Payment interface
//        System.out.println("\n=== 12. Paying orders ===");
//        Payment upi = new UpiPayment("asha@okbank");
//        Payment goodCard = new CardPayment("4111111111111111");
//        Payment declinedCard = new CardPayment("4111111111110000");   // ends in 0000 -> declined
//
//        // Order #2 is CREATED (unpaid) -> pay by UPI
//        PaymentResult upiResult = orderService.pay(2, upi);
//        System.out.println("order 2 by UPI : " + upiResult.status() + " -> status "
//                + orderService.findById(2).map(Order::getStatus).orElse(null) + " (expect SUCCESS -> PAID)");
//
//        try {
//            Order order4 = orderService.placeOrder(4, Map.of(ballPen.getId(), 1));
//            // Declined card: order must stay CREATED
//            PaymentResult declined = orderService.pay(order4.getId(), declinedCard);
//            System.out.println("order 4 by declined card : " + declined.status() + " -> status "
//                    + order4.getStatus() + " (expect FAILED -> CREATED)");
//            // Retry with a good card
//            PaymentResult retry = orderService.pay(order4.getId(), goodCard);
//            System.out.println("order 4 retry, good card : " + retry.status() + " -> status "
//                    + order4.getStatus() + " (expect SUCCESS -> PAID)");
//            System.out.println("   amount charged = " + retry.amount() + " (expect 10.50)");
//        } catch (InsufficientStockException e) {
//            System.out.println("FAIL: setup order was rejected: " + e.getMessage());
//        }
//
//        // Paying an order that is already PROCESSING / CANCELLED must be rejected BEFORE charging
//        expectInvalidTransition("pay PROCESSING order 1", () -> orderService.pay(1, goodCard));
//        expectInvalidTransition("pay CANCELLED order 3", () -> orderService.pay(3, upi));
//        expectRejected("pay unknown order 999", () -> orderService.pay(999, upi));
//
//        // A brand-new payment type, written as a lambda: OrderService needed NO change (Open/Closed)
//        Payment freeVoucher = amount -> new PaymentResult("VOUCHER-1", PaymentMethod.UPI,
//                PaymentStatus.SUCCESS, amount, java.time.Instant.now(), "paid by voucher");
//        try {
//            Order order5 = orderService.placeOrder(5, Map.of(diary.getId(), 1));
//            orderService.pay(order5.getId(), freeVoucher);
//            System.out.println("order 5 by voucher lambda -> status " + order5.getStatus() + " (expect PAID)");
//        } catch (InsufficientStockException e) {
//            System.out.println("FAIL: setup order was rejected: " + e.getMessage());
//        }
//
//        System.out.println("revenue now = " + orderService.revenueByProduct()
//                + " (expect Ball Pen 21.00+10.50+10.50 = 42.00, Diary 45.00+45.00 = 90.00)");
//    }
//
//    private static void expectInvalidTransition(String label, Runnable action) {
//        try {
//            action.run();
//            System.out.println("FAIL: " + label + " was accepted");
//        } catch (InvalidTransitionException e) {
//            System.out.println("OK, rejected (" + label + "): " + e.getMessage());
//        }
//    }
//
//    // Runs the given action and reports whether it was rejected with IllegalArgumentException.
//    private static void expectRejected(String label, Runnable action) {
//        try {
//            action.run();
//            System.out.println("FAIL: " + label + " was accepted");
//        } catch (IllegalArgumentException e) {
//            System.out.println("OK, rejected (" + label + "): " + e.getMessage());
//        }
    }
}