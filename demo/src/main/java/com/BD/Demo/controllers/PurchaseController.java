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

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
import com.BD.Demo.dto.request.PurchaseRequestDTO;
import com.BD.Demo.dto.response.PurchaseResponseDTO;
import com.BD.Demo.entities.Purchase;
import com.BD.Demo.services.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
<<<<<<< HEAD
@Tag(name = "Purchase", description = "Purchase management APIs")
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

<<<<<<< HEAD
    @Operation(summary = "Get all purchases", description = "Returns a list of all purchases with their batches")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved purchases list")
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
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

<<<<<<< HEAD
    @Operation(summary = "Create new purchase", description = "Creates a new purchase with batches")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Purchase successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
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
