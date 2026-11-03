package org.siddardha.service;

import org.siddardha.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductService {
    ConcurrentHashMap<Integer, Product> productMap = new ConcurrentHashMap<>();

    List<Product> findall() {
        List<Product> products = new ArrayList<>();
        productMap.forEach((key, value) -> products.add(value));
        return products;
    }
}
