package com.example.msvc_purchases.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BatchDTO {
    private Long Id;
    private String productCode;
    private String batchNumber;
    private Long purchaseOrderId;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private BigDecimal unitCost;
}

