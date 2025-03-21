package com.microservices.inventory.controller;

import com.microservices.inventory.dto.BatchDTO;
import com.microservices.inventory.dto.StockUpdateRequestDTO;
import com.microservices.inventory.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO) {
        return ResponseEntity.ok(batchService.createBatch(batchDTO));
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDTO> getBatchById(@PathVariable Long batchId) {
        BatchDTO batch = batchService.getBatchById(batchId);
        return batch != null ? ResponseEntity.ok(batch) : ResponseEntity.notFound().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<BatchDTO>> getAvailableBatches() {
        return ResponseEntity.ok(batchService.getAvailableBatches());
    }

    @GetMapping("/product/{productCod}")
    public ResponseEntity<List<BatchDTO>> getBatchesByProduct(@PathVariable String productCod) {
        return ResponseEntity.ok(batchService.getBatchesByProduct(productCod));
    }

    @GetMapping("/{batchId}/check-availability")
    public ResponseEntity<Boolean> checkBatchAvailability(
            @PathVariable Long batchId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(batchService.checkBatchAvailability(batchId, quantity));
    }

    @PostMapping("/update-stock")
    public ResponseEntity<Void> updateBatchStock(@RequestBody StockUpdateRequestDTO updateRequest) {
        batchService.updateBatchStock(updateRequest);
        return ResponseEntity.ok().build();
    }
} 