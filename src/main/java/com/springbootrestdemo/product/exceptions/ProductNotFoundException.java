package com.springbootrestdemo.product.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Product not found";

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
