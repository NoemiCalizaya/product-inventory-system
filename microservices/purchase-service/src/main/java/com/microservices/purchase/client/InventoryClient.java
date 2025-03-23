package com.microservices.purchase.client;

import com.microservices.purchase.dto.BatchDTO;
import com.microservices.purchase.dto.PurchaseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${inventory.service.url}")
public interface InventoryClient {

    @PostMapping("/api/batches")
    ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batchDTO);

    @PostMapping("/api/purchases")
    ResponseEntity<PurchaseDTO> createPurchase(@RequestBody PurchaseDTO purchaseDTO);
}
