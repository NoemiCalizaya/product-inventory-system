package com.example.msvc_sales.infrastructure.entities;

import com.example.msvc_sales.infrastructure.controllers.entities.utils.PaymentStatus;
import com.example.msvc_sales.infrastructure.controllers.entities.utils.SalesOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_order_id")
    private Long salesOrderId;

    @Column(name = "order_number", nullable = false, unique = true, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SalesOrderStatus status = SalesOrderStatus.DRAFT;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relación con items de la orden
    @OneToMany(mappedBy = "salesOrder", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesOrderItem> items;

    // Métodos de negocio
    public boolean canBeModified() {
        return status == SalesOrderStatus.DRAFT || status == SalesOrderStatus.PENDING;
    }

    public boolean isCompleted() {
        return status == SalesOrderStatus.DELIVERED;
    }

    public BigDecimal getFinalAmount() {
        return subtotal.subtract(discountAmount).add(taxAmount);
    }

    public void calculateTotals() {
        if (items != null && !items.isEmpty()) {
            subtotal = items.stream()
                    .map(SalesOrderItem::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            totalAmount = getFinalAmount();
        }
    }

    @PrePersist
    @PreUpdate
    private void validateOrder() {
        if (requiredDate != null && orderDate != null &&
                requiredDate.isBefore(orderDate)) {
            throw new IllegalArgumentException(
                    "Required date cannot be before order date");
        }

        if (discountAmount.compareTo(subtotal) > 0) {
            throw new IllegalArgumentException(
                    "Discount amount cannot exceed subtotal");
        }

        calculateTotals();
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
                "salesOrderId=" + salesOrderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
