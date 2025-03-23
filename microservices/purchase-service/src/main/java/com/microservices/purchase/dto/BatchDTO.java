package com.microservices.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDTO {
    private Long batchId;
    private String batchNumber;
    private String productCod;
    private Long purchaseId;
    private Integer quantity;
    private Integer availableQuantity;
    private BigDecimal costUnit;
    private LocalDate expirationDate;
    private LocalDate manufacturingDate;
    private String state;
}
