package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
