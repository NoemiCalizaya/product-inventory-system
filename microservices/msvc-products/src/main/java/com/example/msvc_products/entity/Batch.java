package com.example.msvc_products.entity;

import com.example.msvc_products.utils.BatchStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "batches",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "batch_number"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long Id;
    @Column(name = "product_code", nullable = false)
    private String productCode;
    @Column(name = "batch_number", nullable = false, length = 50)
    private String batchNumber;
    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;
    @Column(name = "initial_quantity", nullable = false)
    private Integer initialQuantity;
    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity;
    @Column(name = "unit_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitCost;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BatchStatus status = BatchStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relaciones JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", referencedColumnName = "product_code", insertable = false, updatable = false)
    private Product product;
}
