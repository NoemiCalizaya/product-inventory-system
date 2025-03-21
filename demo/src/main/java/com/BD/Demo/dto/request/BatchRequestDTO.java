package com.BD.Demo.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class BatchRequestDTO {
    private String productCod; 
    private String batchNumber;  
    private LocalDate expirationDate;  
    private LocalDate manufacturingDate;  
    private int quantity; 
    private BigDecimal costUnit;
}

