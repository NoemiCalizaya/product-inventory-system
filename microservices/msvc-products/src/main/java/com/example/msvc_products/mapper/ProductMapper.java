package com.example.msvc_products.mapper;

import com.example.msvc_products.dto.CategoryDTO;
import com.example.msvc_products.dto.ProductDTO;
import com.example.msvc_products.entity.Category;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.utils.CategoryStatus;
import com.example.msvc_products.utils.ProductStatus;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getProductCode(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getId(),
                product.getUnitOfMeasure(),
                product.getMinStockLevel(),
                product.getMaxStockLevel(),
                product.getUnitCost(),
                product.getSelling_price(),
                product.getStatus()
        );
    }

    public static Product toEntity(ProductDTO dto, Category category) {
        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(category);
        product.setUnitOfMeasure(dto.getUnitOfMeasure());
        product.setMinStockLevel(dto.getMinStockLevel());
        product.setMaxStockLevel(dto.getMaxStockLevel());
        product.setUnitCost(dto.getUnitCost());
        product.setSelling_price(dto.getSelling_price());
        product.setStatus(ProductStatus.ACTIVE);
        return product;
    }
}
