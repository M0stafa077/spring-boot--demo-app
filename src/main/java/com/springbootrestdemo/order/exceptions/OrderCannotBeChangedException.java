package com.springbootrestdemo.order.exceptions;

import com.springbootrestdemo.global.exceptions.BadRequestException;

public class OrderCannotBeChangedException extends BadRequestException {
    public OrderCannotBeChangedException(String message) {
        super(message);
    }
}
