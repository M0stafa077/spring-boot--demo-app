package com.springbootrestdemo.order;

import com.springbootrestdemo.auth.annotations.AdminOnlyAccess;
import com.springbootrestdemo.auth.annotations.CurrentUser;
import com.springbootrestdemo.global.shared.ApiResponse;
import com.springbootrestdemo.order.dtos.AddAndRemoveProductToOrderRequestDto;
import com.springbootrestdemo.order.dtos.CreateOrderDto;
import com.springbootrestdemo.product.ProductService;
import com.springbootrestdemo.users.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Iterable<Order>> findMyOrders(@CurrentUser User currentUser) {
        var orders = this.orderService.findAll(currentUser);
        return ApiResponse.success(orders);
    }

    @GetMapping("/{id}")
    public ApiResponse<Order> findById(@PathVariable Long id,
                                       @CurrentUser User currentUser) {
        var order = this.orderService.findByIdAndUser(id, currentUser);
        return ApiResponse.success(order);
    }

    @PostMapping
    public ApiResponse<Order> create(
            @Valid @RequestBody CreateOrderDto createOrderDto,
            @CurrentUser User currentUser
    ) {
        var order = this.orderService.create(createOrderDto, currentUser);
        return ApiResponse.success(order);
    }

    @PatchMapping("/add-product")
    public ApiResponse<Order> addProductToOrder(
            @Valid @RequestBody AddAndRemoveProductToOrderRequestDto details,
            @CurrentUser User currentUser
    ) {
        var order = this.orderService.addProductToOrder(details.orderId(), details.productId(), currentUser);
        return ApiResponse.success(order);
    }

    @PatchMapping("/remove-product")
    public ApiResponse<Order> removeProductFromOrder(
            @Valid @RequestBody AddAndRemoveProductToOrderRequestDto details,
            @CurrentUser User currentUser
    ) {
        var order = this.orderService.removeProductFromOrder(details.orderId(), details.productId(), currentUser);
        return ApiResponse.success(order);
    }

    // should be admin-only
    @PatchMapping("/{id}/confirm")
    @AdminOnlyAccess
    public ApiResponse<String> confirmOrder(@PathVariable Long id) {
        this.orderService.confirmOrder(id);
        return ApiResponse.success("Order has been confirmed");
    }

    @PatchMapping("/{id}/cancel")
    public ApiResponse<String> cancelOrder(
            @PathVariable Long id,
            @CurrentUser User currentUser
    ) {
        this.orderService.cancelOrder(id, currentUser);
        return ApiResponse.success("Order has been canceled");
    }

    // should be admin-only
    @PatchMapping("/{id}/ship")
    @AdminOnlyAccess
    public ApiResponse<String> shipOrder(@PathVariable Long id) {
        this.orderService.shipOrder(id);
        return ApiResponse.success("Order has been shipped");
    }

    // should be admin-only
    @PatchMapping("/{id}/deliver")
    @AdminOnlyAccess
    public ApiResponse<String> deliverOrder(@PathVariable Long id) {
        this.orderService.deliverOrder(id);
        return ApiResponse.success("Order has been delivered");
    }
}
