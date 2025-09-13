package com.example.msvc_products.dto;

import com.example.msvc_products.utils.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementDTO {

    private Long movementId;
    private Long batchId;
    private MovementType movementType;
    private Integer quantity;
    private BigDecimal unitCost;
    private Integer previousQuantity;
    private Integer newQuantity;
}
