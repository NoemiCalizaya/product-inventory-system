package com.example.msvc_sales.infrastructure.entities;

import com.example.msvc_sales.infrastructure.controllers.entities.utils.SalesItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_item_id")
    private Long salesItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @Column(name = "product_code", length = 50)
    private String productCode;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SalesItemStatus status = SalesItemStatus.PENDING;

    @PrePersist
    @PreUpdate
    private void calculateTotalAmount() {
        if (unitPrice != null && quantityOrdered != null) {
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantityOrdered));

            if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal discountAmount = lineTotal
                        .multiply(discountPercentage)
                        .divide(BigDecimal.valueOf(100));
                totalAmount = lineTotal.subtract(discountAmount);
            } else {
                totalAmount = lineTotal;
            }
        }
    }
}
