package com.microservices.purchase.controller;

import com.microservices.purchase.dto.PurchaseRequestDTO;
import com.microservices.purchase.dto.PurchaseResponseDTO;
import com.microservices.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO requestDTO) {
        return ResponseEntity.ok(purchaseService.createPurchase(requestDTO));
    }
} 