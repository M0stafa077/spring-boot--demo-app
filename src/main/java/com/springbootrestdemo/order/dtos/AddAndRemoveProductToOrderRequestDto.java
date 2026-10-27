package com.springbootrestdemo.order.dtos;

import jakarta.validation.constraints.NotNull;

public record AddAndRemoveProductToOrderRequestDto(
        @NotNull
        long orderId,
        @NotNull
        long productId
) {
}
