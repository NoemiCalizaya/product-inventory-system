package com.example.msvc_products.entity;

import com.example.msvc_products.utils.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_code", unique = true, nullable = false, length = 50)
    private String productCode;
    @Column(nullable = false, length = 255)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Solo la relación, JPA maneja el ID automáticamente
    @Column(name = "unit_of_measure", length = 20)
    private String unitOfMeasure;
    @Column(name = "min_stock_level")
    private Integer minStockLevel = 0;
    @Column(name = "max_stock_level")
    private Integer maxStockLevel;
    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;
    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal selling_price;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProductStatus status = ProductStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
