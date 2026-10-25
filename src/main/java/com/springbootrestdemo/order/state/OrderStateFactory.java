package com.springbootrestdemo.order.state;

import java.util.Map;

public class OrderStateFactory {
    private static final Map<String, OrderState> statesMap = Map.of(
            new PendingState().getName(), new PendingState(),
            new ConfirmedState().getName(), new ConfirmedState(),
            new CanceledState().getName(), new CanceledState(),
            new ShippedState().getName(), new ShippedState(),
            new DeliveredState().getName(), new DeliveredState()
    );

    public static OrderState of(String stateName) {
        return statesMap.get(stateName);
    }
}
