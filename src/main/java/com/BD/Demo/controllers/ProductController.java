package com.BD.Demo.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.entities.Product;
import com.BD.Demo.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> productsList() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable String product_cod) {
        Optional<Product> productOptional = productService.findById(product_cod);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String product_cod, @RequestBody Product updatedProduct) {
        Optional<Product> existingProduct = productService.findById(product_cod);
        if (existingProduct.isPresent()) {
            return ResponseEntity.ok(productService.saveProduct(updatedProduct));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String product_cod) {
        Optional<Product> existingProduct = productService.findById(product_cod);
        if (existingProduct.isPresent()) {
            productService.deleteProduct(product_cod);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
