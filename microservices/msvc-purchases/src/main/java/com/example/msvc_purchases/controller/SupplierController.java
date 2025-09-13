package com.example.msvc_purchases.controller;

import com.example.msvc_purchases.dto.SupplierDTO;
import com.example.msvc_purchases.entity.Supplier;
import com.example.msvc_purchases.mapper.SupplierMapper;
import com.example.msvc_purchases.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping("/create")
    public ResponseEntity<SupplierDTO> create(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = new SupplierMapper().toEntity(supplierDTO);
        Supplier saved = supplierService.create(supplier);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SupplierMapper().toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getById(id);
        SupplierDTO supplierDTO = new SupplierMapper().toDTO(supplier);

        return ResponseEntity.ok(supplierDTO);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAll();

        if(suppliers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<SupplierDTO> supplierDTOS = new SupplierMapper().toDTOList(suppliers);

        return ResponseEntity.ok(supplierDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO newSupplier) {
        Supplier supplier = supplierService.update(id, new SupplierMapper().toEntity(newSupplier));

        SupplierDTO supplierDTO = new SupplierMapper().toDTO(supplier);

        return ResponseEntity.ok(supplierDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(Long id) {
        supplierService.getById(id);

        return ResponseEntity.noContent().build();
    }
}
