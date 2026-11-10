package org.siddardha.controller;

import jakarta.validation.Valid;
import org.siddardha.Product;
import org.siddardha.dto.CreateProductRequest;
import org.siddardha.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getListofProducts() {
        return productService.findall();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product create(@Valid @RequestBody CreateProductRequest product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable int id, @RequestBody Product product) {
        return productService.update(id,product);
    }


}
