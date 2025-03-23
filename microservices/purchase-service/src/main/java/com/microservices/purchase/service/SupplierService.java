package com.microservices.purchase.service;

import com.microservices.purchase.dto.SupplierDTO;
import com.microservices.purchase.entity.Supplier;
import com.microservices.purchase.repository.SupplierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        validateSupplierData(supplierDTO);
        
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        
        if (supplier.getState() == null) {
            supplier.setState("ACTIVE");
        }
        
        supplier = supplierRepository.save(supplier);
        
        SupplierDTO responseDTO = new SupplierDTO();
        BeanUtils.copyProperties(supplier, responseDTO);
        return responseDTO;
    }

    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierDTO getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("No se encontró el proveedor con ID: " + supplierId));
        return convertToDTO(supplier);
    }

    @Transactional
    public SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("No se encontró el proveedor con ID: " + supplierId));
        
        validateSupplierData(supplierDTO);
        
        BeanUtils.copyProperties(supplierDTO, supplier, "supplierId");
        supplier = supplierRepository.save(supplier);
        
        SupplierDTO responseDTO = new SupplierDTO();
        BeanUtils.copyProperties(supplier, responseDTO);
        return responseDTO;
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new RuntimeException("No se encontró el proveedor con ID: " + supplierId);
        }
        supplierRepository.deleteById(supplierId);
    }

    private void validateSupplierData(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            throw new RuntimeException("Los datos del proveedor no pueden ser nulos");
        }
        if (supplierDTO.getSupplierName() == null || supplierDTO.getSupplierName().trim().isEmpty()) {
            throw new RuntimeException("El nombre del proveedor es requerido");
        }
    }

    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        BeanUtils.copyProperties(supplier, dto);
        return dto;
    }
}
