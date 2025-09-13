package com.example.msvc_products.controller;

import com.example.msvc_products.dto.CategoryDTO;
import com.example.msvc_products.entity.Category;
import com.example.msvc_products.mapper.CategoryMapper;
import com.example.msvc_products.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category saved = categoryService.save(category);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<Category> categories = categoryService.getAll();

        // Convertir cada entidad a DTO
        List<CategoryDTO> dtos = categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        Category category = categoryService.getCategoryId(id);
        return ResponseEntity.ok(CategoryMapper.toDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO newCategoryDTO) {
        Category newCategory = CategoryMapper.toEntity(newCategoryDTO);
        Category updated = categoryService.update(id, newCategory);

        return ResponseEntity.ok(CategoryMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
