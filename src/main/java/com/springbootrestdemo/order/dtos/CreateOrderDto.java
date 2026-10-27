package com.springbootrestdemo.order.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDto(
        @NotEmpty
        List<@NotNull Long> product_ids
) {
}
