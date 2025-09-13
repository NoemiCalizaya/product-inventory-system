package com.example.msvc_products.repository;

import com.example.msvc_products.entity.Category;
import com.example.msvc_products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategory_Id(Long categoryId);

}
