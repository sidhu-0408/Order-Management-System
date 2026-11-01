package org.siddardha;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final Inventory inventory;
    private final Map<Integer, Product> products;

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
        return new Order(userId, items);
    }
}
