package com.example.msvc_products.mapper;

import com.example.msvc_products.dto.StockMovementDTO;
import com.example.msvc_products.entity.StockMovement;
import com.example.msvc_products.entity.Batch;

import java.util.List;
import java.util.stream.Collectors;

public class StockMovementMapper {

    public static StockMovement toEntity(StockMovementDTO dto, Batch batch) {
        if (dto == null) return null;

        StockMovement movement = new StockMovement();
        movement.setMovementId(dto.getMovementId());
        movement.setBatch(batch); // relación con Batch
        movement.setMovementType(dto.getMovementType());
        movement.setQuantity(dto.getQuantity());
        movement.setUnitCost(dto.getUnitCost());
        movement.setPreviousQuantity(dto.getPreviousQuantity());
        movement.setNewQuantity(dto.getNewQuantity());

        return movement;
    }

    public static StockMovementDTO toDTO(StockMovement movement) {
        if (movement == null) return null;

        StockMovementDTO dto = new StockMovementDTO();
        dto.setMovementId(movement.getMovementId());
        dto.setBatchId(movement.getBatch().getId());
        dto.setMovementType(movement.getMovementType());
        dto.setQuantity(movement.getQuantity());
        dto.setUnitCost(movement.getUnitCost());
        dto.setPreviousQuantity(movement.getPreviousQuantity());
        dto.setNewQuantity(movement.getNewQuantity());

        return dto;
    }

    public static List<StockMovementDTO> toDTOList(List<StockMovement> movements) {
        return movements.stream().map(StockMovementMapper::toDTO).collect(Collectors.toList());
    }
}
