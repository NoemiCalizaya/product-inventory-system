package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.entities.Supplier;
import com.BD.Demo.repositories.SupplierRepository;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
