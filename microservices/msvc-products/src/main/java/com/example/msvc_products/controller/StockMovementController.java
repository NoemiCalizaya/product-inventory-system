package com.example.msvc_products.controller;

import com.example.msvc_products.dto.StockMovementDTO;
import com.example.msvc_products.dto.request.AddQuantityRequest;
import com.example.msvc_products.entity.StockMovement;
import com.example.msvc_products.mapper.StockMovementMapper;
import com.example.msvc_products.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;

    // Movimiento de entrada (IN)
    @PostMapping("/{Id}/in")
    public ResponseEntity<StockMovementDTO> stockMovementIn(
            @PathVariable Long Id,
            @RequestBody AddQuantityRequest request) {

        StockMovement movement = stockMovementService
                .stockMovementIn(Id, request.getQuantity(), request.getUnitCost());
        return ResponseEntity.ok(StockMovementMapper.toDTO(movement));
    }

    // Movimiento de salida (OUT)
    @PostMapping("/{Id}/out")
    public ResponseEntity<StockMovement> stockMovementOut(
            @PathVariable Long Id,
            @RequestParam Integer quantity) {

        StockMovement movement = stockMovementService.stockMovementOut(Id, quantity);
        return ResponseEntity.ok(movement);
    }

    // Consultar movimientos de un batch
    @GetMapping("/batch/{Id}")
    public ResponseEntity<List<StockMovement>> getMovementsByBatch(@PathVariable Long Id) {
        List<StockMovement> movements = stockMovementService.getMovementsByBatch(Id);
        return ResponseEntity.ok(movements);
    }
}
