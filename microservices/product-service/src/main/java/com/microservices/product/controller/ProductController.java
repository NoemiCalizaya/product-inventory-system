package com.microservices.product.controller;

import com.microservices.product.model.Product;
import com.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productCod}")
    public ResponseEntity<Product> getProductById(@PathVariable String productCod) {
        return productService.getProductById(productCod)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Product>> getProductsByState(@PathVariable String state) {
        return ResponseEntity.ok(productService.getProductsByState(state));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/{productCod}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productCod, @RequestBody Product product) {
        return productService.updateProduct(productCod, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productCod}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productCod) {
        productService.deleteProduct(productCod);
        return ResponseEntity.noContent().build();
    }
} 