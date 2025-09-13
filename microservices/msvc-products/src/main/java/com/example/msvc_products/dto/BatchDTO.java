package com.example.msvc_products.dto;

import com.example.msvc_products.utils.BatchStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDTO {
    private Long Id;
    private String productCode;
    private String batchNumber;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private Long purchaseOrderId;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private BigDecimal unitCost;
    private BatchStatus status = BatchStatus.ACTIVE;
}
