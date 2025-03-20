package com.BD.Demo.dto.request;

import java.util.List;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleRequestDTO {
    private String ci;
    private String salesmanName;
    private List<SalesDetailRequestDTO> details;

    // Getters y setters
    public List<SalesDetailRequestDTO> getDetails() {
        return details;
    }

    public void setDetails(List<SalesDetailRequestDTO> details) {
        this.details = details;
    }

    public static class SalesDetailRequestDTO {
        private Long batchId;        // ID del lote
        private String productName;  // Nombre del producto (para referencia)
        private String batchNumber;  // Número de lote
        private BigDecimal unitPrice; // Precio unitario (pasado desde el frontend)
        private Integer quantity;    // Cantidad vendida
        private BigDecimal subtotal; // Subtotal (cantidad * precio unitario)

        // Getters y setters
        public Long getBatchId() {
            return batchId;
        }

        public void setBatchId(Long batchId) {
            this.batchId = batchId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBatchNumber() {
            return batchNumber;
        }

        public void setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }
}

