package com.example.msvc_products.mapper;

import com.example.msvc_products.dto.CategoryDTO;
import com.example.msvc_products.entity.Category;
import com.example.msvc_products.utils.CategoryStatus;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getStatus()
        );
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getCategoryId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setStatus(CategoryStatus.ACTIVE);
        return category;
    }
}

