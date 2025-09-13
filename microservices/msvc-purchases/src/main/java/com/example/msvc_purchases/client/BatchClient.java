package com.example.msvc_purchases.client;

import com.example.msvc_purchases.dto.BatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-products", contextId = "batchClient", path = "/api/batches")
public interface BatchClient {

    @PostMapping("/create")
    BatchDTO createBatch(@RequestBody BatchDTO batchDTO);

    @GetMapping("/by-purchase/{purchaseOrderId}/product/{productCode}")
    BatchDTO getBatchByPurchaseOrderAndProduct(@PathVariable Long purchaseOrderId,
                                               @PathVariable String productCode);
}
