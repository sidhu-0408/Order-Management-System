package org.siddardha.Repository;

import org.siddardha.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {

    ConcurrentHashMap<Integer, Product> productMap = new ConcurrentHashMap<>();

    public List<Product> findall() {
        List<Product> products = new ArrayList<>();
        productMap.forEach((key, value) -> products.add(value));
        return products;
    }

    public Product findById(int id) {
        return productMap.get(id);
    }

    public Product create(Product product) {
        productMap.put(product.getId(), product);
        return productMap.get(product.getId());
    }

    public Product update(int id, Product product) {
        productMap.put(id,product);
        return productMap.get(id);
    }
}
