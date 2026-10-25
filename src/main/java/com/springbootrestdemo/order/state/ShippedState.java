package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;
import com.springbootrestdemo.order.exceptions.IllegalOrderStateTransitionException;

public class ShippedState implements OrderState {
    @Override
    public String getName() {
        return "Shipped";
    }

    @Override
    public void confirm(Order order) {
        throw new IllegalOrderStateTransitionException("Order has been shipped");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalOrderStateTransitionException("Order has been already shipped");
    }

    @Override
    public void deliver(Order order) {
        order.setState(new DeliveredState());
        order.setStateName(order.getState().getName());
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalOrderStateTransitionException("Order has been shipped");
    }

    @Override
    public boolean canBeChanged() {
        return false;
    }
}
