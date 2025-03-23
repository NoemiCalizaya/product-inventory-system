package com.microservices.sales.client;

import com.microservices.sales.dto.ProductPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
    @GetMapping("/api/products/batch/{batchId}/price")
    ResponseEntity<ProductPriceDTO> getProductPrice(@PathVariable("batchId") Long batchId);
}