package com.microservices.user.controller;

import com.microservices.user.dto.CreateSalesmanRequest;
import com.microservices.user.dto.SalesmanResponse;
import com.microservices.user.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salesmen")
public class SalesmanController {
    
    @Autowired
    private SalesmanService salesmanService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesmanResponse createSalesman(@RequestBody CreateSalesmanRequest request) {
        return salesmanService.createSalesman(request);
    }
    
    @GetMapping("/{ci}")
    public SalesmanResponse getSalesmanByCi(@PathVariable String ci) {
        return salesmanService.getSalesmanByCi(ci);
    }
    
    @GetMapping
    public List<SalesmanResponse> getAllSalesmen() {
        return salesmanService.getAllSalesmen();
    }
    
    @GetMapping("/state/{state}")
    public List<SalesmanResponse> getSalesmenByState(@PathVariable String state) {
        return salesmanService.getSalesmenByState(state);
    }
    
    @PatchMapping("/{ci}/state")
    public SalesmanResponse updateSalesmanState(@PathVariable String ci, @RequestParam String state) {
        return salesmanService.updateSalesmanState(ci, state);
    }
} 