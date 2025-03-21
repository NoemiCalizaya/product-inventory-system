package com.BD.Demo.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @Column(name = "product_cod", length = 50, nullable = false)
    private String productCod;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
    
    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;
    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;
    @Column(name = "unit_measure")
    private String unit_measure;
    @Column(name = "profit_margin", precision = 10, scale = 2)
    private BigDecimal profitMargin;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
