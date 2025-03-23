package com.microservices.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseDTO {
    private Long purchaseId;
    private String salesmanCi;
    private Long supplierId;
    private LocalDate dateAcquisition;
    private String state;
    private BigDecimal purchaseCost;
}
