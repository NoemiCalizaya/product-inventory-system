package com.example.msvc_inventory.service;

import com.example.msvc_inventory.entity.Inventory;
import com.example.msvc_inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public Inventory addStock(String productCode, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductCode(productCode)
                .map(inv -> {
                    inv.setQuantityAvailable(inv.getQuantityAvailable() + quantity);
                    inv.setLastUpdated(LocalDateTime.now());
                    return inv;
                })
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setProductCode(productCode);
                    inv.setQuantityAvailable(quantity);
                    inv.setQuantityOrdered(0);
                    inv.setLastUpdated(LocalDateTime.now());
                    return inv;
                });

        return inventoryRepository.save(inventory);
    }

}
