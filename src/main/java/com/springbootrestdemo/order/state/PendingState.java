package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;
import com.springbootrestdemo.order.exceptions.IllegalOrderStateTransitionException;

public class PendingState implements OrderState {
    @Override
    public String getName() {
        return "Pending";
    }

    @Override
    public void confirm(Order order) {
        order.setState(new ConfirmedState());
        order.setStateName(order.getState().getName());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalOrderStateTransitionException("Canceled order cannot be shipped");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalOrderStateTransitionException("Canceled order cannot be delivered  ");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CanceledState());
        order.setStateName(order.getState().getName());
    }

    @Override
    public boolean canBeChanged() {
        return true;
    }
}
