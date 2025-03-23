package com.microservices.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productCod;
    private Long categoryId;
    private String categoryDescription;
    private String productName;
    private Double salePrice;
    private String unitMeasure;
    private Double profitMargin;
    private String state;
}
