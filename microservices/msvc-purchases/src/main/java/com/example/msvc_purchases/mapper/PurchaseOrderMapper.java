package com.example.msvc_purchases.mapper;

import com.example.msvc_purchases.dto.PurchaseOrderDTO;
import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderMapper {

    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            return null;
        }

        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        dto.setPoNumber(purchaseOrder.getPoNumber());
        dto.setSupplierId(purchaseOrder.getSupplier() != null ?
                purchaseOrder.getSupplier().getSupplierId() : null);
        dto.setOrderDate(purchaseOrder.getOrderDate());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setSubtotal(purchaseOrder.getSubtotal());
        dto.setTotalAmount(purchaseOrder.getTotalAmount());

        return dto;
    }

    public PurchaseOrder toEntity(PurchaseOrderDTO dto) {
        if (dto == null) {
            return null;
        }

        PurchaseOrder.PurchaseOrderBuilder builder = PurchaseOrder.builder()
                .purchaseOrderId(dto.getPurchaseOrderId())
                .poNumber(dto.getPoNumber())
                .orderDate(dto.getOrderDate())
                .status(dto.getStatus())
                .subtotal(dto.getSubtotal())
                .totalAmount(dto.getTotalAmount());

        // Para el supplier, solo creamos una referencia con el ID
        if (dto.getSupplierId() != null) {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(dto.getSupplierId());
            builder.supplier(supplier);
        }

        return builder.build();
    }

    public void updateEntityFromDTO(PurchaseOrderDTO dto, PurchaseOrder purchaseOrder) {
        if (dto == null || purchaseOrder == null) {
            return;
        }

        purchaseOrder.setPoNumber(dto.getPoNumber());
        purchaseOrder.setOrderDate(dto.getOrderDate());
        purchaseOrder.setStatus(dto.getStatus());
        purchaseOrder.setSubtotal(dto.getSubtotal());
        purchaseOrder.setTotalAmount(dto.getTotalAmount());

        // Para actualizar el supplier, necesitarías cargarlo desde la base de datos
        if (dto.getSupplierId() != null) {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(dto.getSupplierId());
            purchaseOrder.setSupplier(supplier);
        }
    }

    public List<PurchaseOrderDTO> toDTOList(List<PurchaseOrder> purchaseOrders) {
        if (purchaseOrders == null) {
            return null;
        }

        return purchaseOrders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PurchaseOrder> toEntityList(List<PurchaseOrderDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
