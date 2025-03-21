package com.BD.Demo.dto.response;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private String productName;
    private Long totalQuantity; // O el tipo que corresponde al resultado de SUM
    private BigDecimal salePrice;

    // Constructor que acepta los parámetros de la consulta HQL
    public ProductResponseDTO(String productName, Long totalQuantity, BigDecimal salePrice) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.salePrice = salePrice;
    }

    // Getters y setters (si es necesario)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
