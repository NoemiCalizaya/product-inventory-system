package com.BD.Demo.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.BD.Demo.entities.Supplier;

import lombok.Data;

@Data
public class PurchaseRequestDTO {
    private Supplier supplier;
    private LocalDate dateAcquisition;
    private BigDecimal purchaseCost;
    private List<BatchRequestDTO> batches;
}

