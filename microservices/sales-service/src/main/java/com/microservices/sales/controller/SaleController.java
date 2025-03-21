package com.microservices.sales.controller;

import com.microservices.sales.dto.SaleRequestDTO;
import com.microservices.sales.dto.SaleResponseDTO;
import com.microservices.sales.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO requestDTO) {
        return ResponseEntity.ok(saleService.createSale(requestDTO));
    }

    @GetMapping("/salesman/{salesmanCi}")
    public ResponseEntity<List<SaleResponseDTO>> getSalesBySalesman(@PathVariable String salesmanCi) {
        return ResponseEntity.ok(saleService.getSalesBySalesman(salesmanCi));
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long saleId) {
        SaleResponseDTO sale = saleService.getSaleById(saleId);
        return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{saleId}/state")
    public ResponseEntity<SaleResponseDTO> updateSaleState(
            @PathVariable Long saleId,
            @RequestParam String newState) {
        return ResponseEntity.ok(saleService.updateSaleState(saleId, newState));
    }
} 