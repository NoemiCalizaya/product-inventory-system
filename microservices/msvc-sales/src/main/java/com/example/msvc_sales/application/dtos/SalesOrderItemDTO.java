package com.example.msvc_sales.application.dtos;

import com.example.msvc_sales.infrastructure.controllers.entities.utils.SalesItemStatus;
import com.example.msvc_sales.infrastructure.entities.SalesOrder;

import java.math.BigDecimal;

public class SalesOrderItemDTO {
    private Long salesItemId;
    private com.example.msvc_sales.infrastructure.entities.SalesOrder salesOrder;
    private String productCode;
    private Integer quantityOrdered;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage = BigDecimal.ZERO;
    private BigDecimal totalAmount;
    private SalesItemStatus status = SalesItemStatus.PENDING;
}
