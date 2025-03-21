package com.microservices.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_cod")
    private String productCod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @Column(name = "unit_measure", nullable = false)
    private String unitMeasure;

    @Column(name = "profit_margin")
    private Double profitMargin;

    private String state;
} 