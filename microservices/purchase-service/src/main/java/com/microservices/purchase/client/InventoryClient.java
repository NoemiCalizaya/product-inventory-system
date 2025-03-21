package com.microservices.purchase.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservices.purchase.dto.BatchDTO;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {
    
    @PostMapping("/api/batches")
    ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO);
}
