package com.microservices.sales.client;

import com.microservices.sales.dto.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {
    @GetMapping("/api/batches/{batchId}/check-availability")
    ResponseEntity<Boolean> checkBatchAvailability(
        @PathVariable("batchId") Long batchId,
        @RequestParam("quantity") Integer quantity
    );
    
    @PostMapping("/api/batches/update-stock")
    ResponseEntity<Void> updateBatchStock(@RequestBody StockUpdateRequest updateRequest);

    @GetMapping("/api/batches/product/{productCod}")
    ResponseEntity<Long> findBatchByProductCode(@PathVariable("productCod") String productCod);
}
