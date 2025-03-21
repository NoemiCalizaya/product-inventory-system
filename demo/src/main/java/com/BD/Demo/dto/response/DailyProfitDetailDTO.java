package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DailyProfitDetailDTO {

    private LocalDate saleDate;
    private BigDecimal totalProfit;
    private List<ProductProfitDetailDTO> productDetails;

    public DailyProfitDetailDTO(LocalDate saleDate, BigDecimal totalProfit, List<ProductProfitDetailDTO> productDetails) {
        this.saleDate = saleDate;
        this.totalProfit = totalProfit;
        this.productDetails = productDetails;
    }
}

