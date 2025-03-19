package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDTO {
    private Long saleId;
    private String salesmanName; // En lugar de salesman_ci
    private LocalDate saleDate;
    private BigDecimal totalAmount;
    private boolean state;

    public SalesDTO(Long saleId, String salesmanName, LocalDate saleDate, BigDecimal totalAmount, boolean state) {
        this.saleId = saleId;
        this.salesmanName = salesmanName;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.state = state;
    }
}
