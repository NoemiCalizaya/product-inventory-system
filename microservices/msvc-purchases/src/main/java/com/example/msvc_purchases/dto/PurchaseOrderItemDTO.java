package com.example.msvc_purchases.dto;

import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.utils.PurchaseItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItemDTO {
    private Long poItemId;
    private Long purchaseOrderId;
    private String productCode;
    private Integer quantityOrdered;
    private Integer quantityReceived = 0;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private PurchaseItemStatus status = PurchaseItemStatus.PENDING;
}
