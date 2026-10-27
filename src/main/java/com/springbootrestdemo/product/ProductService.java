package com.springbootrestdemo.product;

import com.springbootrestdemo.product.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Iterable<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findById(Long id) {
        var product = this.productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return product.get();
    }

    public List<Product> findAllByIn(List<Long> product_ids) {
        return this.productRepository.findAllByIdIn(product_ids);
    }

    public Product create(Product product) {
        product.setId(null);
        return this.productRepository.save(product);
    }

    public Product modify(long id, Product product) {
        this.findById(id);
        product.setId(id);
        return this.productRepository.save(product);
    }

    public void delete(long id) {
        this.findById(id);
        this.productRepository.deleteById(id);
    }
}