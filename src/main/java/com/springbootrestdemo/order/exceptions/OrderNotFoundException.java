package com.springbootrestdemo.order.exceptions;

import com.springbootrestdemo.global.exceptions.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Order not found";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
