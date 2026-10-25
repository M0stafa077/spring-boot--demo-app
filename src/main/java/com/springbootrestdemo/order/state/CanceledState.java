package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;
import com.springbootrestdemo.order.exceptions.IllegalOrderStateTransitionException;

public class CanceledState implements OrderState {
    @Override
    public String getName() {
        return "Canceled";
    }

    @Override
    public void confirm(Order order) {
        throw new IllegalOrderStateTransitionException("Canceled orders cannot be confirmed");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalOrderStateTransitionException("Canceled orders cannot be shipped");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalOrderStateTransitionException("Canceled orders cannot be delivered");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalOrderStateTransitionException("Order has been already canceled");
    }

    @Override
    public boolean canBeChanged() {
        return false;
    }
}
