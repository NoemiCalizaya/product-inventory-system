package com.example.msvc_purchases.service;

import com.example.msvc_purchases.entity.Supplier;
import com.example.msvc_purchases.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier getById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier update(Long id, Supplier newSupplier) {
        return supplierRepository.findById(id)
                .map(supplier -> {
                   newSupplier.setSupplierId(supplier.getSupplierId());
                   return supplierRepository.save(newSupplier);
                }).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public void delete(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplierRepository.delete(supplier);
    }
}
