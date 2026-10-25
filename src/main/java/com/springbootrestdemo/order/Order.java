package com.springbootrestdemo.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springbootrestdemo.global.shared.BaseEntity;
import com.springbootrestdemo.order.state.OrderState;
import com.springbootrestdemo.order.state.OrderStateFactory;
import com.springbootrestdemo.order.state.PendingState;
import com.springbootrestdemo.product.Product;
import com.springbootrestdemo.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stateName = new PendingState().getName();

    @Transient
    private OrderState state = new PendingState();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Long getTotalPrice() {
        return this.items.stream()
                .mapToLong(i -> i.getUnitPrice() * i.getQuantity())
                .sum();
    }

    public void addProduct(Product product) {
        var item = new OrderItem();
        item.setOrder(this);
        item.setProduct(product);
        item.setUnitPrice(product.getPrice());
        this.items.add(item);
    }

    public void removeProduct(Product product) {
        this.items.removeIf(i -> i.getProduct().getId().equals(product.getId()));
    }

    public void confirm() {
        this.state.confirm(this);
    }

    public void ship() {
        this.state.ship(this);
    }

    public void deliver() {
        this.state.deliver(this);
    }

    public void cancel() {
        this.state.cancel(this);
    }

    @PostLoad
    private void calculateTransientFields() {
        OrderState state = OrderStateFactory.of(this.stateName);
        this.setState(state);
    }
}
