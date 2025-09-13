package com.wannacode.bookingmicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway")
public interface StockClient {

    @GetMapping("/api/stock/{code}")
    boolean stockAvailable(@PathVariable("code") String code);
}
