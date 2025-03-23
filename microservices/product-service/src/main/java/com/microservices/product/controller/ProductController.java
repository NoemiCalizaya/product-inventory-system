package com.microservices.product.controller;

import com.microservices.product.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productCod}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productCod) {
        return productService.getProductById(productCod)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<ProductDTO>> getProductsByState(@PathVariable String state) {
        return ResponseEntity.ok(productService.getProductsByState(state));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/{productCod}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productCod, @RequestBody Product product) {
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