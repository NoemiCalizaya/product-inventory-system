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

import com.BD.Demo.entities.Supplier;
import com.BD.Demo.services.SupplierService;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<?> suppliersList() {
        return ResponseEntity.ok(this.supplierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSupplier(@PathVariable Long id) {
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if(supplierOptional.isPresent()){
            return ResponseEntity.ok(supplierOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.saveSupplier(supplier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Supplier updatedSupplier) {
        Optional<Supplier> existingSupplier = supplierService.findById(id);
        if (existingSupplier.isPresent()) {
            return ResponseEntity.ok(supplierService.saveSupplier(updatedSupplier));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        Optional<Supplier> existingSupplier = supplierService.findById(id);
        if (existingSupplier.isPresent()) {
            supplierService.deleteSupplier(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
