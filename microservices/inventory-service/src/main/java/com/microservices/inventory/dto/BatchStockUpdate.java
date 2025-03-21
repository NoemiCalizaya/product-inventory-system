package com.microservices.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockUpdate {
    private Long batchId;
    private Integer quantity;
    private String operation; // "DECREASE" para ventas, "INCREASE" para devoluciones
} 