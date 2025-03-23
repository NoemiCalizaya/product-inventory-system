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

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
import com.BD.Demo.entities.Supplier;
import com.BD.Demo.services.SupplierService;

@RestController
@RequestMapping("/api/supplier")
<<<<<<< HEAD
@Tag(name = "Supplier", description = "Supplier management APIs")
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

<<<<<<< HEAD
    @Operation(summary = "Get all suppliers", description = "Returns a list of all suppliers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved suppliers list")
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
    @GetMapping
    public ResponseEntity<?> suppliersList() {
        return ResponseEntity.ok(this.supplierService.findAll());
    }

<<<<<<< HEAD
    @Operation(summary = "Get supplier by ID", description = "Returns a supplier by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved supplier"),
        @ApiResponse(responseCode = "404", description = "Supplier not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findSupplier(@Parameter(description = "Supplier ID") @PathVariable Long id) {
=======
    @GetMapping("/{id}")
    public ResponseEntity<?> findSupplier(@PathVariable Long id) {
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if(supplierOptional.isPresent()){
            return ResponseEntity.ok(supplierOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

<<<<<<< HEAD
    @Operation(summary = "Create new supplier", description = "Creates a new supplier")
    @ApiResponse(responseCode = "200", description = "Supplier successfully created")
=======
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
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
