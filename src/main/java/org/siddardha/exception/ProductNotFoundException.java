package org.siddardha.exception;

// Unchecked, so service and controller methods don't need "throws" clauses.
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(int id) {
        super("Product " + id + " not found");
    }
}
