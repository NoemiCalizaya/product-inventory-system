package com.BD.Demo.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductProfitDetailDTO {

    private String productName;
    private int quantitySold;
    private BigDecimal salePrice;
    private BigDecimal revenue;
    private BigDecimal profit;
}

