package com.example.msvc_products.dto;

import com.example.msvc_products.entity.Category;
import com.example.msvc_products.utils.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String productCode;
    private String name;
    private String description;
    private Long categoryId;
    private String unitOfMeasure;
    private Integer minStockLevel = 0;
    private Integer maxStockLevel;
    private BigDecimal unitCost;
    private BigDecimal selling_price;
    private ProductStatus status = ProductStatus.ACTIVE;
}
