package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfitDetailDTO {
    private LocalDate saleDate;
    private String productName;
    private BigDecimal salePrice;
    private int quantity;
    private BigDecimal totalSale;

    public ProfitDetailDTO(BigDecimal totalSale) {
        this.totalSale = totalSale;
    }
}
