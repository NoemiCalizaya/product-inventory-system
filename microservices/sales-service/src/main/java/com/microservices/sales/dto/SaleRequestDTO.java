package com.microservices.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestDTO {
    private String salesmanCi;
    private List<SaleDetailRequestDTO> details;
} 