package com.example.msvc_purchases.dto;

import com.example.msvc_purchases.entity.Supplier;
import com.example.msvc_purchases.utils.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDTO {

    private Long purchaseOrderId;
    private String poNumber;
    private Long supplierId;
    private LocalDateTime orderDate;
    private PurchaseOrderStatus status = PurchaseOrderStatus.DRAFT;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
