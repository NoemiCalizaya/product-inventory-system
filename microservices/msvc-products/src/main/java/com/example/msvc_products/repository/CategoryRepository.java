package com.example.msvc_products.repository;

import com.example.msvc_products.entity.Category;
import com.example.msvc_products.utils.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
