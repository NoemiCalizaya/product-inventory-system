package com.BD.Demo.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.entities.SalesDetail;
import com.BD.Demo.services.SalesDetailService;

@RestController
@RequestMapping("/api/sales-detail")
public class SalesDetailController {
    private final SalesDetailService salesDetailService;

    public SalesDetailController(SalesDetailService salesDetailService) {
        this.salesDetailService = salesDetailService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.salesDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<SalesDetail> salesDetailOptional = salesDetailService.findById(id);
        if(salesDetailOptional.isPresent()){
            return ResponseEntity.ok(salesDetailOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createSalesDetail(@RequestBody SalesDetail SalesDetail) {
        return ResponseEntity.ok(salesDetailService.saveSalesDetail(SalesDetail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalesDetail(@PathVariable Long id, @RequestBody SalesDetail updatedSalesDetail) {
        Optional<SalesDetail> existingSalesDetail = salesDetailService.findById(id);
        if (existingSalesDetail.isPresent()) {
            return ResponseEntity.ok(salesDetailService.saveSalesDetail(updatedSalesDetail));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalesDetail(@PathVariable Long id) {
        Optional<SalesDetail> existingSalesDetail = salesDetailService.findById(id);
        if (existingSalesDetail.isPresent()) {
            salesDetailService.deleteSalesDetail(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
