package com.example.msvc_products.mapper;

import com.example.msvc_products.dto.BatchDTO;
import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class BatchMapper {

    public static Batch toEntity(BatchDTO dto, Product product) {
        if (dto == null) return null;

        Batch batch = new Batch();
        batch.setId(dto.getId());
        batch.setProductCode(product.getProductCode());
        batch.setProduct(product);
        batch.setBatchNumber(dto.getBatchNumber());
        batch.setManufacturingDate(dto.getManufacturingDate());
        batch.setExpiryDate(dto.getExpiryDate());
        batch.setPurchaseOrderId(dto.getPurchaseOrderId()); // Solo almacenamos el ID
        batch.setInitialQuantity(dto.getInitialQuantity());
        batch.setCurrentQuantity(dto.getCurrentQuantity());
        batch.setUnitCost(dto.getUnitCost());
        batch.setStatus(dto.getStatus());
        return batch;
    }

    public static BatchDTO toDTO(Batch batch) {
        if (batch == null) return null;

        BatchDTO dto = new BatchDTO();
        dto.setId(batch.getId());
        dto.setProductCode(batch.getProduct().getProductCode());
        dto.setBatchNumber(batch.getBatchNumber());
        dto.setManufacturingDate(batch.getManufacturingDate());
        dto.setExpiryDate(batch.getExpiryDate());
        dto.setPurchaseOrderId(batch.getPurchaseOrderId()); // Solo el ID
        dto.setInitialQuantity(batch.getInitialQuantity());
        dto.setCurrentQuantity(batch.getCurrentQuantity());
        dto.setUnitCost(batch.getUnitCost());
        dto.setStatus(batch.getStatus());
        return dto;
    }

    public static List<BatchDTO> toDTOList(List<Batch> batches) {
        return batches.stream().map(BatchMapper::toDTO).collect(Collectors.toList());
    }
}
