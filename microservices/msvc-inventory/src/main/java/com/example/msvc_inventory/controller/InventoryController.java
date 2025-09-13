package com.example.msvc_inventory.controller;

import com.example.msvc_inventory.entity.Inventory;
import com.example.msvc_inventory.service.InventoryService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @PostMapping("/add-stock")
    public ResponseEntity<Inventory> addStock(
            @RequestParam String productCode,
            @RequestParam Integer quantity
    ) {
        Inventory inventory = inventoryService.addStock(productCode, quantity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventory);
    }
}
