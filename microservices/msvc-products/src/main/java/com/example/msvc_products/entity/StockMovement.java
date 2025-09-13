package com.example.msvc_products.entity;

import com.example.msvc_products.utils.MovementType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long movementId;

    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 20)
    private MovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "previous_quantity", nullable = false)
    private Integer previousQuantity;

    @Column(name = "new_quantity", nullable = false)
    private Integer newQuantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", insertable = false, updatable = false)
    private Batch batch;

    public Integer getQuantityDifference() {
        return newQuantity - previousQuantity;
    }

    public BigDecimal getTotalValue() {
        if (unitCost != null) {
            return unitCost.multiply(BigDecimal.valueOf(Math.abs(quantity)));
        }
        return BigDecimal.ZERO;
    }

    public boolean isInboundMovement() {
        return movementType == MovementType.IN ||
                movementType == MovementType.ADJUSTMENT_IN ||
                movementType == MovementType.RETURN_IN;
    }

    public boolean isOutboundMovement() {
        return movementType == MovementType.OUT ||
                movementType == MovementType.ADJUSTMENT_OUT ||
                movementType == MovementType.TRANSFER_OUT;
    }

    @PrePersist
    @PreUpdate
    private void validateMovement() {
        // Validar que la diferencia de cantidades coincida con quantity
        int expectedDifference = Math.abs(newQuantity - previousQuantity);
        if (Math.abs(quantity) != expectedDifference) {
            throw new IllegalArgumentException(
                    "Quantity must match the absolute difference between previous and new quantity");
        }

        // Validar coherencia del tipo de movimiento
        if (isInboundMovement() && newQuantity <= previousQuantity) {
            throw new IllegalArgumentException(
                    "Inbound movements must increase inventory quantity");
        }

        if (isOutboundMovement() && newQuantity >= previousQuantity) {
            throw new IllegalArgumentException(
                    "Outbound movements must decrease inventory quantity");
        }

        // Validar que previous_quantity no sea negativo para outbound
        if (isOutboundMovement() && previousQuantity < Math.abs(quantity)) {
            throw new IllegalArgumentException(
                    "Cannot withdraw more than available quantity");
        }
    }

    // Método estático para crear movimientos
    public static StockMovement createInboundMovement(Long batchId, Integer quantityIn,
                                              Integer previousQty, BigDecimal unitCost) {
        StockMovement stock = new StockMovement();
        stock.batchId = batchId;
        stock.movementType = MovementType.IN;
        stock.quantity = quantityIn;
        stock.previousQuantity = previousQty;
        stock.newQuantity = previousQty + quantityIn;
        stock.unitCost = unitCost;
        return stock;
    }

    public static StockMovement createOutboundMovement(Long batchId, Integer quantityOut,
                                               Integer previousQty, BigDecimal unitCost) {
        StockMovement stock = new StockMovement();
        stock.batchId = batchId;
        stock.movementType = MovementType.OUT;
        stock.quantity = quantityOut;
        stock.previousQuantity = previousQty;
        stock.newQuantity = previousQty - quantityOut;
        stock.unitCost = unitCost;
        return stock;
    }
}
