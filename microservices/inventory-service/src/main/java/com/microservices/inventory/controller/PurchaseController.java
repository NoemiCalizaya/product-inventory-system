package com.microservices.inventory.controller;

import com.microservices.inventory.dto.PurchaseDTO;
import com.microservices.inventory.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseDTO> createPurchase(@RequestBody PurchaseDTO purchaseDTO) {
        PurchaseDTO createdPurchase = purchaseService.createPurchase(purchaseDTO);
        return ResponseEntity.ok(createdPurchase);
    }
}
