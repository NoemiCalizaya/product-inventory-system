package com.example.msvc_purchases.controller;

import com.example.msvc_purchases.dto.PurchaseOrderDTO;
import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.mapper.PurchaseOrderMapper;
import com.example.msvc_purchases.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/purchases")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrderDTO> create(@RequestBody PurchaseOrderDTO purchaseDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrderMapper().toEntity(purchaseDTO);

        PurchaseOrder saved = purchaseOrderService.create(purchaseOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PurchaseOrderMapper().toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.getAll();
        List<PurchaseOrderDTO> purchaseOrderDTOs = new PurchaseOrderMapper().toDTOList(purchaseOrders);

        return ResponseEntity.ok(purchaseOrderDTOs);
    }
}
