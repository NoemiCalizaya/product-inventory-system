package com.BD.Demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.entities.Batch;
import com.BD.Demo.services.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<?> batchesList() {
        return ResponseEntity.ok(this.batchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBatch(@PathVariable Long id) {
        Optional<Batch> batchOptional = batchService.findById(id);
        if(batchOptional.isPresent()){
            return ResponseEntity.ok(batchOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createBatch(@RequestBody Batch Batch) {
        return ResponseEntity.ok(batchService.saveBatch(Batch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBatch(@PathVariable Long id, @RequestBody Batch updatedBatch) {
        Optional<Batch> existingBatch = batchService.findById(id);
        if (existingBatch.isPresent()) {
            return ResponseEntity.ok(batchService.saveBatch(updatedBatch));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable Long id) {
        Optional<Batch> existingBatch = batchService.findById(id);
        if (existingBatch.isPresent()) {
            batchService.deleteBatch(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Batch>> getLowStockProducts(@RequestParam int threshold) {
        List<Batch> lowStockBatches = batchService.getLowStockProducts(threshold);
        
        if (lowStockBatches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(lowStockBatches);
    }
}
