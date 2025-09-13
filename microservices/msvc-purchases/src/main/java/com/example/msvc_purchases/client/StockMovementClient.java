package com.example.msvc_purchases.client;

import com.example.msvc_purchases.dto.request.AddQuantityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "msvc-products", contextId = "stockMovementClient", path = "/api/stock-movements")
public interface StockMovementClient {

    @PostMapping("/{Id}/in")
    void stockMovementIn(@PathVariable Long Id,
                         @RequestBody AddQuantityRequest request
    );
}
