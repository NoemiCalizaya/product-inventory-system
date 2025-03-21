package com.microservices.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDTO {
    private Long saleId;
    private String salesmanCi;
    private LocalDate saleDate;
    private BigDecimal totalAmount;
    private String state;
    private List<SaleDetailDTO> details;
} 