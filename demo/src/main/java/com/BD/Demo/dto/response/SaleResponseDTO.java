package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.BD.Demo.entities.Salesman;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class SaleResponseDTO {
    private Long saleId;
    private Salesman salesman;
    private LocalDate saleDate;
    private BigDecimal totalAmount;
    private Boolean state;

    public SaleResponseDTO(Long saleId, Salesman salesman, LocalDate saleDate, BigDecimal totalAmount,
            Boolean state) {
        this.saleId = saleId;
        this.salesman = salesman;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.state = state;
    }
}
