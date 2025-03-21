package com.microservices.purchase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @Column(name = "product_cod")
    private String productCod;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "sale_price")
    private BigDecimal salePrice;
    
    @Column(name = "unit_measure")
    private String unitMeasure;
    
    @Column(name = "profit_margin")
    private BigDecimal profitMargin;
    
    @Column(name = "state")
    private String state;
} 