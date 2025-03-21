package com.BD.Demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseResponseDTO {
    private Long purchaseId;
    private String supplierName;
    private LocalDate dateAcquisition;
    private BigDecimal purchaseCost;
    private Boolean state;
    private List<BatchResponseDTO> batches;

    public PurchaseResponseDTO(Long purchaseId, String supplierName, LocalDate dateAcquisition, BigDecimal purchaseCost,
            Boolean state, List<BatchResponseDTO> batches) {
        this.purchaseId = purchaseId;
        this.supplierName = supplierName;
        this.dateAcquisition = dateAcquisition;
        this.purchaseCost = purchaseCost;
        this.state = state;
        this.batches = batches;
    }

    public PurchaseResponseDTO(Long purchaseId, String supplierName, LocalDate dateAcquisition, BigDecimal purchaseCost,
                            Boolean state) {
        this.purchaseId = purchaseId;
        this.supplierName = supplierName;
        this.dateAcquisition = dateAcquisition;
        this.purchaseCost = purchaseCost;
        this.state = state;
    }
}
