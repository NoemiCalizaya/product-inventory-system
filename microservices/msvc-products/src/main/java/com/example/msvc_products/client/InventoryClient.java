package com.example.msvc_products.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-inventory", path = "/api/inventories")
public interface InventoryClient {

    @PostMapping("/add-stock")
    void addStock(@RequestParam String productCode, @RequestParam int quantity);
}

