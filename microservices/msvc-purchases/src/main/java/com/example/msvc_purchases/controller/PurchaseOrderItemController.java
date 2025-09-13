package com.example.msvc_purchases.controller;

import com.example.msvc_purchases.dto.PurchaseOrderItemDTO;
import com.example.msvc_purchases.dto.request.AddQuantityRequest;
import com.example.msvc_purchases.entity.PurchaseOrderItem;
import com.example.msvc_purchases.mapper.PurchaseOrderItemMapper;
import com.example.msvc_purchases.service.PurchaseOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-items")
public class PurchaseOrderItemController {

    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    // Crear un item
    @PostMapping("/create")
    public ResponseEntity<PurchaseOrderItemDTO> create(@RequestBody PurchaseOrderItemDTO itemDTO) {
        PurchaseOrderItem created = purchaseOrderItemService.create(new PurchaseOrderItemMapper().toEntity(itemDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PurchaseOrderItemMapper().toDTO(created));
    }

    // Obtener todos los items
    @GetMapping
    public ResponseEntity<List<PurchaseOrderItemDTO>> getAll() {
        List<PurchaseOrderItem> purchases = purchaseOrderItemService.getAll();

        return ResponseEntity.ok(new PurchaseOrderItemMapper().toDTOList(purchases));
    }

    // Obtener item por ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderItemDTO> getById(@PathVariable Long id) {
        PurchaseOrderItem purchase = purchaseOrderItemService.getById(id);

        return ResponseEntity.ok(new PurchaseOrderItemMapper().toDTO(purchase));
    }

    // Agregar cantidad a un item existente
    @PutMapping("/{poItemId}/add-quantity")
    public ResponseEntity<PurchaseOrderItemDTO> addQuantity(
            @PathVariable Long poItemId,
            @RequestBody AddQuantityRequest request
            ) {

        PurchaseOrderItem updated = purchaseOrderItemService.addQuantity(
                poItemId,
                request.getQuantity(),
                request.getUnitCost()
        );

        return ResponseEntity.ok(new PurchaseOrderItemMapper().toDTO(updated));
    }
}
