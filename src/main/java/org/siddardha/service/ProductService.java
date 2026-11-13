package org.siddardha.service;

import org.siddardha.Product;
import org.siddardha.repository.ProductRepository;
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
        return productRepository.findAll();
    }

    public Product findById(int id) {
        Product product = productRepository.findById(id);
        if (product == null) throw new ProductNotFoundException(id);
        return product;
    }

    public Product create(CreateProductRequest product) {
        return productRepository.save(toEntity(product));
    }

    public Product update(int id, Product productToUpdate) {
        Product productFromDB = productRepository.findById(id);
        return productRepository.save(productToUpdate(productFromDB,productToUpdate));
    }

    public Product toEntity (CreateProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        product.setCategory(productRequest.category());
        return product;
    }

    private Product productToUpdate(Product productFromDB, Product productToUpdate ) {

        productToUpdate.setName(productToUpdate.getName().isBlank() ? productFromDB.getName() : productToUpdate.getName());
        productToUpdate.setPrice(productToUpdate.getPrice() == null ? productFromDB.getPrice() : productToUpdate.getPrice());
        productToUpdate.setDescription(productToUpdate.getDescription().isBlank() ? productFromDB.getDescription() : productToUpdate.getDescription());
        productToUpdate.setCategory(productToUpdate.getCategory().isBlank() ? productFromDB.getCategory() : productToUpdate.getCategory());

        return productToUpdate;
    }
}
