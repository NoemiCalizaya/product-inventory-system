package com.microservices.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Long purchaseId;
    private Long supplierId;
    private String salesmanCi;
    private LocalDate dateAcquisition;
    private BigDecimal purchaseCost;
    private String state;
    private List<BatchDTO> batches;
}