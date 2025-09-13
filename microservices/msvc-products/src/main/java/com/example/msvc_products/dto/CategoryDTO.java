package com.example.msvc_products.dto;

import com.example.msvc_products.utils.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;
    private String name;
    private String description;
    private CategoryStatus status;
}

