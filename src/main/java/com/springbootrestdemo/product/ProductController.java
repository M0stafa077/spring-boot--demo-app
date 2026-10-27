package com.springbootrestdemo.product;

import com.springbootrestdemo.auth.annotations.AdminOnlyAccess;
import com.springbootrestdemo.global.shared.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ApiResponse<Iterable<Product>> findAll() {
        return ApiResponse.success(this.productService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> findById(@PathVariable Long id) {
        return ApiResponse.success(this.productService.findById(id));
    }

    @PostMapping
    @AdminOnlyAccess
    public ApiResponse<Product> create(@Valid @RequestBody Product product) {
        return ApiResponse.success(this.productService.create(product));
    }

    @PutMapping("/{id}")
    @AdminOnlyAccess
    public ApiResponse<Product> modify(@PathVariable Long id, @Valid @RequestBody Product product) {
        return ApiResponse.success(this.productService.modify(id, product));
    }

    @DeleteMapping("/{id}")
    @AdminOnlyAccess
    public ApiResponse<Null> delete(@PathVariable Long id) {
        this.productService.delete(id);
        return ApiResponse.emptySuccess();
    }
}
