package com.microservices.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDTO {
    private Long supplierId;
    private String salesmanCi;
    private List<BatchDTO> batches;
} 