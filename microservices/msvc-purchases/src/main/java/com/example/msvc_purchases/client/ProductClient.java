package com.example.msvc_purchases.client;

import com.example.msvc_purchases.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "msvc-products", contextId = "productClient", path = "/api/products")
public interface ProductClient {

    @GetMapping("/code/{productCode}")
    ProductDTO getProductByCode(@PathVariable String productCode);

    @PutMapping("/{code}/cost")
    void updateCost(@PathVariable("code") String code,
                    @RequestParam("unitCost") BigDecimal unitCost);
}
