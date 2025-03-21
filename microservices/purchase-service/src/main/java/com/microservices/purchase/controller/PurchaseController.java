package com.microservices.purchase.controller;

import com.microservices.purchase.dto.PurchaseRequestDTO;
import com.microservices.purchase.dto.PurchaseResponseDTO;
import com.microservices.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO requestDTO) {
        return ResponseEntity.ok(purchaseService.createPurchase(requestDTO));
    }

    @GetMapping("/salesman/{salesmanCi}")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesBySalesman(@PathVariable String salesmanCi) {
        return ResponseEntity.ok(purchaseService.getPurchasesBySalesman(salesmanCi));
    }

    @GetMapping("/salesman/{salesmanCi}/state/{state}")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesBySalesmanAndState(
            @PathVariable String salesmanCi,
            @PathVariable String state) {
        return ResponseEntity.ok(purchaseService.getPurchasesBySalesmanAndState(salesmanCi, state));
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long purchaseId) {
        PurchaseResponseDTO purchase = purchaseService.getPurchaseById(purchaseId);
        return purchase != null ? ResponseEntity.ok(purchase) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{purchaseId}/state")
    public ResponseEntity<PurchaseResponseDTO> updatePurchaseState(
            @PathVariable Long purchaseId,
            @RequestParam String newState) {
        return ResponseEntity.ok(purchaseService.updatePurchaseState(purchaseId, newState));
    }
} 