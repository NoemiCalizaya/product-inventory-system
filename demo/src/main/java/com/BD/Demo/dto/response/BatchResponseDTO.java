package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BatchResponseDTO {
    private Long batchId;
    private String productName;
    private String batchNumber;
    private LocalDate expirationDate;
    private LocalDate manufacturingDate;
    private Integer quantity;
    private BigDecimal costUnit;
    
    public BatchResponseDTO(Long batchId, String productName, String batchNumber, LocalDate expirationDate,
            LocalDate manufacturingDate, Integer quantity, BigDecimal costUnit) {
        this.batchId = batchId;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.expirationDate = expirationDate;
        this.manufacturingDate = manufacturingDate;
        this.quantity = quantity;
        this.costUnit = costUnit;
    }
}

