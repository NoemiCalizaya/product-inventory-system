package com.microservices.product.service;

import com.microservices.product.model.Product;
import com.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String productCod) {
        return productRepository.findById(productCod);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    public List<Product> getProductsByState(String state) {
        return productRepository.findByState(state);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(String productCod, Product product) {
        return productRepository.findById(productCod)
                .map(existingProduct -> {
                    product.setProductCod(productCod);
                    return productRepository.save(product);
                });
    }

    public void deleteProduct(String productCod) {
        productRepository.deleteById(productCod);
    }
} 