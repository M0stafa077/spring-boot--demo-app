package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;
import com.springbootrestdemo.order.exceptions.IllegalOrderStateTransitionException;

public class ConfirmedState implements OrderState {
    @Override
    public String getName() {
        return "Confirmed";
    }

    @Override
    public void confirm(Order order) {
        throw new IllegalOrderStateTransitionException("Order has been already confirmed");
    }

    @Override
    public void ship(Order order) {
        order.setState(new ShippedState());
        order.setStateName(order.getState().getName());
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalOrderStateTransitionException("Order should be shipped first");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CanceledState());
        order.setStateName(order.getState().getName());
    }

    @Override
    public boolean canBeChanged() {
        return false;
    }
}
