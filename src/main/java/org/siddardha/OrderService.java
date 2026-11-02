package org.siddardha;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.*;

public class OrderService {

    private final Inventory inventory;
    private final Map<Integer, Product> products;
    private final List<Order> orders = new ArrayList<>();

    public OrderService(Inventory inventory, Map<Integer, Product> products) {
        this.inventory = inventory;
        this.products = products;
    }

    /**
     * Places an order for the given user.
     *
     * @param userId  who is ordering
     * @param request productId -> quantity (a Map, so the same product can't appear twice)
     * @return the new Order in CREATED status
     * @throws InsufficientStockException if any item doesn't have enough stock (nothing is deducted)
     */
    public Order placeOrder(int userId, Map<Integer, Integer> request) throws InsufficientStockException {
        if (request == null || request.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }

        // 1. Validate EVERY item first. Nothing is changed yet.
        for (Map.Entry<Integer, Integer> entry : request.entrySet()) {
            int productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = products.get(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product " + productId + " not found");
            }
            if (!product.isActive()) {
                throw new IllegalArgumentException("Product " + product.getName() + " is not available");
            }
            if (quantity == null || quantity < 1) {
                throw new IllegalArgumentException("Quantity for " + product.getName() + " must be at least 1");
            }
            int available = inventory.getStock(productId);
            if (available < quantity) {
                throw new InsufficientStockException("Not enough stock for " + product.getName()
                        + ": requested " + quantity + ", available " + available);
            }
        }

        // 2. All checks passed: now deduct stock and build the items.
        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : request.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = products.get(productId);

            inventory.deductStock(productId, quantity);
            // Copy name and current price into the item, so later price changes don't affect this order.
            items.add(new OrderItem(productId, product.getName(), quantity, product.getPrice()));
        }

        // 3. Create the order (starts as CREATED).
        Order order = new Order(userId, items);
        orders.add(order);
        return order;
    }

    public Map<Integer, BigDecimal> revenueByProduct() {
        return orders.stream().filter(order -> order.getStatus() != OrderStatus.CREATED && order.getStatus() != OrderStatus.CANCELLED)
                .flatMap(order -> order.getItems().stream())
                .collect(groupingBy(OrderItem::productId, reducing(BigDecimal.ZERO, OrderItem::subTotal, BigDecimal::add)));
    }

    public Map<OrderStatus, List<Order>> ordersByStatus() {
        return orders.stream().collect(groupingBy(Order::getStatus,() -> new EnumMap<>(OrderStatus.class), toList()));
    }

    public Map<OrderStatus, Long> countByStatus() {
        return orders.stream().collect(groupingBy(Order::getStatus, () -> new EnumMap<>(OrderStatus.class), counting()));
    }

    public Optional<Order> findById(int id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst();
    }

    public List<Order> getOrders() {
        return List.copyOf(orders);
    }

    /**
     * Pays for an order with ANY Payment implementation (card, UPI, or one added later).
     * OrderService only knows the Payment interface - that is what makes payment "pluggable"
     * (Dependency Inversion + Open/Closed: a new method needs a new class, not an edit here).
     *
     * @return the PaymentResult; on SUCCESS the order moves to PAID, on FAILED it stays CREATED
     * @throws IllegalArgumentException   if the order doesn't exist or payment is null
     * @throws InvalidTransitionException if the order can't be paid (e.g. already PAID or CANCELLED)
     */
    public PaymentResult pay(int orderId, Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment method required");
        }
        Order order = findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order " + orderId + " not found"));

        // Check BEFORE charging: never take money for an order we can't move to PAID.
        if (!order.getStatus().canTransitionTo(OrderStatus.PAID)) {
            throw new InvalidTransitionException(
                    "Order " + orderId + " cannot be paid in status " + order.getStatus());
        }

        PaymentResult result = payment.processPayment(order.getTotal());
        if (result.status() == PaymentStatus.SUCCESS) {
            order.changeStatus(OrderStatus.PAID);
        }
        return result;
    }
}
