package com.BD.Demo.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.dto.request.PurchaseRequestDTO;
import com.BD.Demo.dto.request.SaleRequestDTO;
import com.BD.Demo.dto.response.PurchaseResponseDTO;
import com.BD.Demo.dto.response.SaleResponseDTO;
import com.BD.Demo.entities.Sale;
import com.BD.Demo.services.SaleService;

@RestController
@RequestMapping("/api/sale")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<?> salesList() {
        return ResponseEntity.ok(saleService.findAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSale(@PathVariable Long id) {
        Optional<Sale> saleOptional = saleService.findById(id);
        if(saleOptional.isPresent()){
            return ResponseEntity.ok(saleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO saleRequest) {
        SaleResponseDTO saleResponse = saleService.createSale(saleRequest);
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody Sale updatedSale) {
        Optional<Sale> existingSale = saleService.findById(id);
        if (existingSale.isPresent()) {
            return ResponseEntity.ok(saleService.saveSale(updatedSale));
        }
        return ResponseEntity.notFound().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        Optional<Sale> existingSale = saleService.findById(id);
        if (existingSale.isPresent()) {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
