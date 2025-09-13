package com.example.msvc_products.service;

import com.example.msvc_products.client.InventoryClient;
import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.entity.StockMovement;
import com.example.msvc_products.repository.BatchRepository;
import com.example.msvc_products.repository.StockMovementRepository;
import com.example.msvc_products.utils.MovementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StockMovementService {

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private InventoryClient inventoryClient;

    public StockMovement stockMovementIn(Long batchId, Integer quantity, BigDecimal unitCost) {
        // 1. Buscar el batch
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        // 2. Buscar el último movimiento para ese batch
        StockMovement lastMovement = stockMovementRepository
                .findTopByBatchOrderByCreatedAtDesc(batch)
                .orElse(null);

        int previousQuantity = (lastMovement != null) ? lastMovement.getNewQuantity() : 0;

        // 3. Crear el nuevo movimiento (IN)
        StockMovement movement = new StockMovement();
        movement.setBatchId(batch.getId());
        movement.setBatch(batch);
        movement.setMovementType(MovementType.IN);
        movement.setQuantity(quantity);
        movement.setUnitCost(unitCost);
        movement.setPreviousQuantity(previousQuantity);
        movement.setNewQuantity(previousQuantity + quantity);

        // 4. Actualizar el lote
        batch.setCurrentQuantity(movement.getNewQuantity());
        batchRepository.save(batch);
        // Product
        Product product = batch.getProduct();
        product.setUnitCost(unitCost);

        // Inventory
        inventoryClient.addStock(batch.getProductCode(), movement.getNewQuantity());

        // 5. Guardar el movimiento
        return stockMovementRepository.save(movement);
    }

    public StockMovement stockMovementOut(Long Id, Integer quantity) {
        Batch batch = batchRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        StockMovement lastMovement = stockMovementRepository
                .findTopByBatchOrderByCreatedAtDesc(batch)
                .orElse(null);

        int previousQuantity = (lastMovement != null) ? lastMovement.getNewQuantity() : 0;

        if (previousQuantity < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        StockMovement movement = new StockMovement();
        movement.setBatch(batch);
        movement.setMovementType(MovementType.OUT);
        movement.setQuantity(quantity);
        movement.setUnitCost(batch.getUnitCost());
        movement.setPreviousQuantity(previousQuantity);
        movement.setNewQuantity(previousQuantity - quantity);

        batch.setCurrentQuantity(movement.getNewQuantity());
        batchRepository.save(batch);

        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getMovementsByBatch(Long Id) {
        Batch batch = batchRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return stockMovementRepository.findByBatchOrderByCreatedAtAsc(batch);
    }

    /*
    private StockMovement createMovement(Batch batch, MovementType type, Integer quantity, BigDecimal unitCost) {
        StockMovement lastMovement = stockMovementRepository
                .findTopByBatchOrderByCreatedAtDesc(batch)
                .orElse(null);

        int previousQuantity = (lastMovement != null) ? lastMovement.getNewQuantity() : 0;
        int newQuantity = (type == MovementType.IN)
                ? previousQuantity + quantity
                : previousQuantity - quantity;

        if (type == MovementType.OUT && newQuantity < 0) {
            throw new RuntimeException("Not enough stock available");
        }

        StockMovement movement = new StockMovement();
        movement.setBatch(batch);
        movement.setMovementType(type);
        movement.setQuantity(quantity);
        movement.setUnitCost(unitCost);
        movement.setPreviousQuantity(previousQuantity);
        movement.setNewQuantity(newQuantity);

        batch.setCurrentQuantity(newQuantity);
        batchRepository.save(batch);

        return stockMovementRepository.save(movement);
    }

    @Transactional
    public StockMovement stockMovementIn(Long batchId, Integer quantity, BigDecimal unitCost) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return createMovement(batch, MovementType.IN, quantity, unitCost);
    }

    @Transactional
    public StockMovement stockMovementOut(Long batchId, Integer quantity) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return createMovement(batch, MovementType.OUT, quantity, batch.getUnitCost());
    }
     */

}

