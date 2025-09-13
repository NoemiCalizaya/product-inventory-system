package com.example.msvc_purchases.service;

import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.entity.Supplier;
import com.example.msvc_purchases.repository.PurchaseOrderRepository;
import com.example.msvc_purchases.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    SupplierRepository supplierRepository;

    public PurchaseOrder create(PurchaseOrder purchaseOrder) {
        Supplier supplier = supplierRepository
                .findById(
                        purchaseOrder.getSupplier().getSupplierId()
                ).orElseThrow(() -> new RuntimeException("Supplier not found"));

        purchaseOrder.setSupplier(supplier);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepository.findAll();
    }
}
