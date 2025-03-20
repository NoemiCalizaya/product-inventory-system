package com.BD.Demo.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.dto.request.PurchaseRequestDTO;
import com.BD.Demo.dto.response.PurchaseResponseDTO;
import com.BD.Demo.entities.Purchase;
import com.BD.Demo.services.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<?> purchasesList() {
        return ResponseEntity.ok(this.purchaseService.findAllPurchasesWithBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPurchase(@PathVariable Long id) {
        Optional<Purchase> purchaseOptional = purchaseService.findById(id);
        if(purchaseOptional.isPresent()){
            return ResponseEntity.ok(purchaseOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO purchaseRequest) {
        PurchaseResponseDTO purchaseResponse = purchaseService.createPurchase(purchaseRequest);
        return new ResponseEntity<>(purchaseResponse, HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable Long id, @RequestBody Purchase updatedPurchase) {
        Optional<Purchase> existingPurchase = purchaseService.findById(id);
        if (existingPurchase.isPresent()) {
            return ResponseEntity.ok(purchaseService.savePurchase(updatedPurchase));
        }
        return ResponseEntity.notFound().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable Long id) {
        Optional<Purchase> existingPurchase = purchaseService.findById(id);
        if (existingPurchase.isPresent()) {
            purchaseService.deletePurchase(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
