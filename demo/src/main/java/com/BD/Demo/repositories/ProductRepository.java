package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    
}
