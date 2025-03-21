package com.microservices.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailRequestDTO {
    private Long batchId;
    private Integer quantity;
} 