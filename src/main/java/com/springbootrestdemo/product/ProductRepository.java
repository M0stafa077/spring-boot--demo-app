package com.springbootrestdemo.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(exported = false)

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByIdIn(Collection<Long> ids);
}
