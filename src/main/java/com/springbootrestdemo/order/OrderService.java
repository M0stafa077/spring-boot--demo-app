package com.springbootrestdemo.order;

import com.springbootrestdemo.order.dtos.CreateOrderDto;
import com.springbootrestdemo.order.exceptions.OrderCannotBeChangedException;
import com.springbootrestdemo.order.exceptions.OrderNotFoundException;
import com.springbootrestdemo.product.Product;
import com.springbootrestdemo.product.ProductService;
import com.springbootrestdemo.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<Order> findAll(User user) {
        return this.orderRepository.findAllByUser(user);
    }

    public Order findById(Long id) {
        var order = this.orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return order.get();
    }

    public Order findByIdAndUser(Long id, User user) {
        var order = this.orderRepository.findByIdAndUser(id, user);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return order.get();
    }

    public Order create(CreateOrderDto createOrderDto, User user) {
        var products = this.productService
                .findAllByIn(createOrderDto.product_ids())
                .stream()
                .filter(Objects::nonNull)
                .toList();
        var order = new Order();
        order.setUser(user);
        products.forEach(order::addProduct);

        return this.orderRepository.save(order);
    }

    public Order addProductToOrder(Order order, Product product, User user) {
        assertUserAccessOnOrder(user, order);
        assertAbilityOfOrderToChange(order);

        order.addProduct(product);
        this.orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order addProductToOrder(long orderId, long productId, User user) {
        var order = this.findByIdAndUser(orderId, user);
        var product = this.productService.findById(productId);

        return this.addProductToOrder(order, product, user);
    }

    public Order removeProductFromOrder(Order order, Product product, User user) {
        assertUserAccessOnOrder(user, order);
        assertAbilityOfOrderToChange(order);

        order.removeProduct(product);
        this.orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order removeProductFromOrder(long orderId, long productId, User user) {
        var order = this.findByIdAndUser(orderId, user);
        var product = this.productService.findById(productId);

        return this.removeProductFromOrder(order, product, user);
    }

    public void confirmOrder(Order order) {
        order.confirm();
        this.orderRepository.save(order);
    }

    @Transactional
    public void confirmOrder(Long orderId) {
        var order = this.findById(orderId);
        this.confirmOrder(order);
    }

    public void cancelOrder(Order order, User user) {
        assertUserAccessOnOrder(user, order);
        order.cancel();
        this.orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId, User user) {
        var order = this.findById(orderId);
        this.cancelOrder(order, user);
    }

    public void shipOrder(Order order) {
        order.ship();
        this.orderRepository.save(order);
    }

    @Transactional
    public void shipOrder(Long orderId) {
        var order = this.findById(orderId);
        this.shipOrder(order);
    }

    public void deliverOrder(Order order) {
        order.deliver();
        this.orderRepository.save(order);
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        var order = this.findById(orderId);
        this.deliverOrder(order);
    }

    private void assertUserAccessOnOrder(User user, Order order) {
        if (!order.getUser().equals(user)) {
            throw new AccessDeniedException("You are not allowed to modify this order");
        }
    }

    private void assertAbilityOfOrderToChange(Order order) {
        if (!order.getState().canBeChanged()) {
            throw new OrderCannotBeChangedException(
                    order.getStateName() + " orders cannot be modified"
            );
        }
    }
}