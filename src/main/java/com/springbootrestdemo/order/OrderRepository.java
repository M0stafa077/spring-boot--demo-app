package com.springbootrestdemo.order;

import com.springbootrestdemo.users.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends CrudRepository<Order, Long> {
    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Order> findAllByUser(User user);

    Optional<Order> findByIdAndUser(Long id, User user);

    List<Order> findAllByUserId(Long id);
}
