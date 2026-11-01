package org.siddardha;

public class InsufficientStockException extends Exception {

    public InsufficientStockException(String errorMessage) {
        super(errorMessage);
    }
}
