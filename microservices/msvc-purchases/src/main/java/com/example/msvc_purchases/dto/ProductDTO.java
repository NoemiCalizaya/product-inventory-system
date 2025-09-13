package com.example.msvc_purchases.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String productCode;
    private String name;
    private String description;
    private BigDecimal unitCost;
    private String status;
}
