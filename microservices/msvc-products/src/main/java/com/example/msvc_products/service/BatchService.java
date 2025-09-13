package com.example.msvc_products.service;

import com.example.msvc_products.client.InventoryClient;
import com.example.msvc_products.dto.BatchDTO;
import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.entity.StockMovement;
import com.example.msvc_products.mapper.BatchMapper;
import com.example.msvc_products.repository.BatchRepository;
import com.example.msvc_products.repository.ProductRepository;
import com.example.msvc_products.repository.StockMovementRepository;
import com.example.msvc_products.utils.MovementType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BatchService {

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockMovementRepository stockMovementRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional
    public Batch create(Batch batch) {
        // Validamos que el producto exista
        String productCode = batch.getProduct().getProductCode();
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        batch.setProduct(product);

        // Guardamos el batch
        Batch savedBatch = batchRepository.save(batch);

        // Registramos movimiento de stock inicial
        StockMovement initialMovement = new StockMovement();
        initialMovement.setBatchId(savedBatch.getId());
        initialMovement.setMovementType(MovementType.IN);
        initialMovement.setQuantity(savedBatch.getInitialQuantity());
        initialMovement.setUnitCost(savedBatch.getUnitCost());
        initialMovement.setPreviousQuantity(0);
        initialMovement.setNewQuantity(savedBatch.getInitialQuantity());

        stockMovementRepository.save(initialMovement);

        // Actualizamos stock en inventario
        System.out.println("productCode=" + product.getProductCode() + "\n" + "batchQuantity=" + savedBatch.getInitialQuantity());
        inventoryClient.addStock(product.getProductCode(), savedBatch.getInitialQuantity());

        return savedBatch;
    }


    public Batch getBatchByPurchaseOrderAndProduct(Long purchaseOrderId, String productCode) {
        Batch batch = batchRepository.findByPurchaseOrderIdAndProductCode(purchaseOrderId, productCode)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        return batch;
    }

}
