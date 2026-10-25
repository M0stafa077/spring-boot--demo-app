package com.springbootrestdemo.order.exceptions;

import com.springbootrestdemo.global.exceptions.BadRequestException;

public class IllegalOrderStateTransitionException extends BadRequestException {
    public IllegalOrderStateTransitionException(String message) {
        super(message);
    }
}
