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
    private String productCod;
    private String batchNumber;
    private LocalDate expirationDate;
    private LocalDate manufacturingDate;
    private Integer quantity;
    private BigDecimal costUnit;
    private String state;
    private Long purchaseId; // Agregado para la relación con la compra
}
