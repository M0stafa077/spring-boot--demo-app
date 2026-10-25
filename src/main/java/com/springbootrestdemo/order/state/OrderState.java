package com.springbootrestdemo.order.state;

import com.springbootrestdemo.order.Order;

import java.util.Map;

public interface OrderState {
    void confirm(Order order);

    void ship(Order order);

    void deliver(Order order);

    void cancel(Order order);

    boolean canBeChanged();

    String getName();

    default OrderState of(String name) {
        final Map<String, OrderState> statesMap = Map.of(
                new PendingState().getName(), new PendingState(),
                new ShippedState().getName(), new ShippedState(),
                new ShippedState().getName(), new ShippedState(),
                new ConfirmedState().getName(), new ConfirmedState(),
                new CanceledState().getName(), new CanceledState()
        );
        return statesMap.get(name);
    }
}
