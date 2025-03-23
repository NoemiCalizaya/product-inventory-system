package com.BD.Demo.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TotalProfitDTO {
    private BigDecimal totalSale;

    public TotalProfitDTO(BigDecimal totalSale) {
        this.totalSale = totalSale;
    }
}

