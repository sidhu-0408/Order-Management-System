package org.siddardha;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<Integer, Integer> stock = new HashMap<>();

    public void addStock(Integer productId, Integer quantity) {
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than 0");

        stock.put(productId, stock.getOrDefault(productId,0) + quantity);
    }

    public Integer getStock(Integer productId) {
        return stock.getOrDefault(productId,0);
    }

    public void deductStock(Integer productID, Integer quantity) {
        if(quantity <= 0)
            throw new IllegalArgumentException("quantity should be greater than 0");

        Integer available = stock.getOrDefault(productID,0);
        if(available < quantity)
            throw new IllegalArgumentException("Current stock is less");

        stock.put(productID, available - quantity);
    }


    //TODO: Need to update restoreLogic later moved to springboot
    public void restoreStock(Integer productId, Integer quantity) {
        addStock(productId, quantity);
    }
}
