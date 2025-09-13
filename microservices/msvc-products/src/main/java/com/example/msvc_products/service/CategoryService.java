package com.example.msvc_products.service;

import com.example.msvc_products.entity.Category;
import com.example.msvc_products.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryId(Long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category update(Long id, Category newCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    newCategory.setId(category.getId());
                    return categoryRepository.save(newCategory);

                }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
}
