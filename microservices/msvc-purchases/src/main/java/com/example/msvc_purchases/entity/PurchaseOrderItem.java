package com.example.msvc_purchases.entity;

import com.example.msvc_purchases.utils.PurchaseItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "quantity_received")
    private Integer quantityReceived = 0;

    @Column(name = "unit_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PurchaseItemStatus status = PurchaseItemStatus.PENDING;
}
