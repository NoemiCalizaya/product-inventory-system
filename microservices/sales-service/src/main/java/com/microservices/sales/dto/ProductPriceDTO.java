package com.microservices.sales.dto;

import java.math.BigDecimal;

public class ProductPriceDTO {
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}