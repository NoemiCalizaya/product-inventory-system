package com.microservices.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long purchaseId;
    private String salesmanCi;
    private Long supplierId;
    private LocalDate dateAcquisition;
    private String state;
    private BigDecimal purchaseCost;
}
