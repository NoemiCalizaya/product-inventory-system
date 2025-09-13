package com.example.msvc_purchases.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddQuantityRequest {
    private Integer quantity;
    private BigDecimal unitCost;
}

