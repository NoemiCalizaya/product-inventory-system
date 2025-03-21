package com.microservices.product.repository;

import com.microservices.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByCategoryCategoryId(Long categoryId);
    List<Product> findByState(String state);
} 