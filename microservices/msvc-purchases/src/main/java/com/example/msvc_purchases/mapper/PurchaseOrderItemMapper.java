package com.example.msvc_purchases.mapper;

import com.example.msvc_purchases.dto.PurchaseOrderItemDTO;
import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.entity.PurchaseOrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderItemMapper {

    public PurchaseOrderItemDTO toDTO(PurchaseOrderItem item) {
        if (item == null) {
            return null;
        }

        PurchaseOrderItemDTO dto = new PurchaseOrderItemDTO();
        dto.setPoItemId(item.getPoItemId());
        dto.setPurchaseOrderId(item.getPurchaseOrder() != null ?
                item.getPurchaseOrder().getPurchaseOrderId() : null);
        dto.setProductCode(item.getProductCode());
        dto.setQuantityOrdered(item.getQuantityOrdered());
        dto.setQuantityReceived(item.getQuantityReceived());
        dto.setUnitCost(item.getUnitCost());
        dto.setTotalCost(item.getTotalCost());
        dto.setStatus(item.getStatus());

        return dto;
    }

    public PurchaseOrderItem toEntity(PurchaseOrderItemDTO dto) {
        if (dto == null) {
            return null;
        }

        PurchaseOrderItem.PurchaseOrderItemBuilder builder = PurchaseOrderItem.builder()
                .poItemId(dto.getPoItemId())
                .productCode(dto.getProductCode())
                .quantityOrdered(dto.getQuantityOrdered())
                .quantityReceived(dto.getQuantityReceived())
                .unitCost(dto.getUnitCost())
                .totalCost(dto.getTotalCost())
                .status(dto.getStatus());

        // Para el purchaseOrder, crear referencia solo con el ID
        if (dto.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderId(dto.getPurchaseOrderId());
            builder.purchaseOrder(purchaseOrder);
        }

        return builder.build();
    }

    public void updateEntityFromDTO(PurchaseOrderItemDTO dto, PurchaseOrderItem item) {
        if (dto == null || item == null) {
            return;
        }

        item.setProductCode(dto.getProductCode());
        item.setQuantityOrdered(dto.getQuantityOrdered());
        item.setQuantityReceived(dto.getQuantityReceived());
        item.setUnitCost(dto.getUnitCost());
        item.setTotalCost(dto.getTotalCost());
        item.setStatus(dto.getStatus());
    }

    public List<PurchaseOrderItemDTO> toDTOList(List<PurchaseOrderItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderItem> toEntityList(List<PurchaseOrderItemDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
