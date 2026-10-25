package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;
import com.springbootrestdemo.order.exceptions.IllegalOrderStateTransitionException;

public class DeliveredState implements OrderState {
    private final RuntimeException exception =
            new IllegalOrderStateTransitionException("Order is already delivered");

    @Override
    public String getName() {
        return "Delivered";
    }

    @Override
    public void confirm(Order order) {
        throw this.exception;
    }

    @Override
    public void ship(Order order) {
        throw this.exception;
    }

    @Override
    public void deliver(Order order) {
        throw this.exception;
    }

    @Override
    public void cancel(Order order) {
        throw this.exception;
    }

    @Override
    public boolean canBeChanged() {
        return false;
    }
}
