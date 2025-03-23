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

import com.BD.Demo.entities.Category;
import com.BD.Demo.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> categoriesList() {
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(categoryOptional.isPresent()){
            return ResponseEntity.ok(categoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isPresent()) {
            return ResponseEntity.ok(categoryService.saveCategory(updatedCategory));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
