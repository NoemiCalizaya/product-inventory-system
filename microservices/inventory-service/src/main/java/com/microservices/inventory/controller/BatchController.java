package com.microservices.inventory.controller;

import com.microservices.inventory.dto.BatchDTO;
import com.microservices.inventory.dto.StockUpdateRequestDTO;
import com.microservices.inventory.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping("/batches")
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        return ResponseEntity.ok(batchService.createBatch(batchDTO));
    }

    @GetMapping("/batches/{batchId}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Long batchId) {
        BatchDTO batch = batchService.getBatchById(batchId);
        return batch != null ? ResponseEntity.ok(batch) : ResponseEntity.notFound().build();
    }

    @GetMapping("/batches/available")
    public ResponseEntity<List<BatchDTO>> getAvailableBatches() {
        return ResponseEntity.ok(batchService.getAvailableBatches());
    }

    @GetMapping("/batches/product/{productCod}")
    public ResponseEntity<List<BatchDTO>> getBatchesByProduct(@PathVariable String productCod) {
        return ResponseEntity.ok(batchService.getBatchesByProduct(productCod));
    }

    @GetMapping("/batches/{batchId}/check-availability")
    public ResponseEntity<Boolean> checkBatchAvailability(
            @PathVariable Long batchId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(batchService.checkBatchAvailability(batchId, quantity));
    }

    @PostMapping("/batches/update-stock")
    public ResponseEntity<Void> updateBatchStock(@RequestBody StockUpdateRequestDTO updateRequest) {
        batchService.updateBatchStock(updateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{productCod}")
    public ResponseEntity<Long> findBatchByProductCode(@PathVariable String productCod) {
        List<BatchDTO> batches = batchService.getBatchesByProduct(productCod);
        if (batches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Return the ID of the first available batch with stock
        return batches.stream()
                .filter(batch -> "ACTIVE".equals(batch.getState()) 
                        && batch.getAvailableQuantity() > 0)
                .map(BatchDTO::getBatchId)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}