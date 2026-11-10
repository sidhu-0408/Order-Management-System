package org.siddardha.service;

import org.siddardha.Product;
import org.siddardha.Repository.ProductRepository;
import org.siddardha.dto.CreateProductRequest;
import org.siddardha.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findall() {
        return productRepository.findall();
    }

    public Product findById(int id) {
        Product product = productRepository.findById(id);
        if (product == null) throw new ProductNotFoundException(id);
        return product;
    }

    public Product create(CreateProductRequest product) {
        return productRepository.create(toEntity(product));
    }

    public Product update(int id, Product product) {
        findById(id);   // throws ProductNotFoundException (-> 404) if the id doesn't exist
        return productRepository.update(id,product);
    }

    public Product toEntity (CreateProductRequest productRequest) {
        Product product = new Product(productRequest.name(), productRequest.price());
        product.setDescription(productRequest.description());
        product.setCategory(productRequest.category());
        return product;
    }
}
