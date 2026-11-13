package org.siddardha.repository;

import org.siddardha.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    ConcurrentHashMap<Integer, Product> productMap = new ConcurrentHashMap<>();

    public List<Product> findAll();

    public Product findById(int id);

    public Product save(Product product);

}
