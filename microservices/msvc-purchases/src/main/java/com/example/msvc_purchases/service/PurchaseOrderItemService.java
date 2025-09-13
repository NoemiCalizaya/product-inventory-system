package com.example.msvc_purchases.service;

import com.example.msvc_purchases.client.BatchClient;
import com.example.msvc_purchases.client.ProductClient;
import com.example.msvc_purchases.client.StockMovementClient;
import com.example.msvc_purchases.dto.BatchDTO;
import com.example.msvc_purchases.dto.ProductDTO;
import com.example.msvc_purchases.dto.request.AddQuantityRequest;
import com.example.msvc_purchases.entity.PurchaseOrder;
import com.example.msvc_purchases.entity.PurchaseOrderItem;
import com.example.msvc_purchases.repository.PurchaseOrderItemRepository;
import com.example.msvc_purchases.repository.PurchaseOrderRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaseOrderItemService {

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private BatchClient batchClient;

    @Autowired
    private StockMovementClient stockMovementClient;

    @Transactional
    public PurchaseOrderItem create(PurchaseOrderItem purchaseOrderItem) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(
                purchaseOrderItem.getPurchaseOrder().getPurchaseOrderId()
        ).orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));

        try {
            ProductDTO product = productClient.getProductByCode(purchaseOrderItem.getProductCode());
            purchaseOrderItem.setProductCode(product.getProductCode());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new RuntimeException("Product not found with code: " + purchaseOrderItem.getProductCode());
            }
            throw new RuntimeException("Error communicating with product service", e);
        }

        Integer quantityOrdered = purchaseOrderItem.getQuantityOrdered() != null
                ? purchaseOrderItem.getQuantityOrdered()
                : 0;

        BigDecimal unitCost = purchaseOrderItem.getUnitCost() != null
                ? purchaseOrderItem.getUnitCost()
                : BigDecimal.ZERO;

        // Calcular total del item
        BigDecimal totalCost = unitCost.multiply(BigDecimal.valueOf(quantityOrdered));
        purchaseOrderItem.setTotalCost(totalCost);
        purchaseOrderItem.setPurchaseOrder(purchaseOrder);

        // 1️⃣ Guardar el item
        PurchaseOrderItem savedItem = purchaseOrderItemRepository.save(purchaseOrderItem);

        // 2️⃣ Crear batch y movimiento inicial en MS productos
        BatchDTO newBatch = new BatchDTO();
        newBatch.setProductCode(savedItem.getProductCode()); // debe existir y no ser null
        newBatch.setBatchNumber("BATCH-" + savedItem.getPoItemId()); // no null
        newBatch.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId()); // no null
        newBatch.setInitialQuantity(savedItem.getQuantityOrdered()); // no null
        newBatch.setCurrentQuantity(savedItem.getQuantityOrdered()); // importante
        newBatch.setUnitCost(savedItem.getUnitCost()); // no null

        System.out.println("productCode=" + savedItem.getProductCode());

        batchClient.createBatch(newBatch);  // 👈 Feign hacia productos

        // 3️⃣ Actualizar costo en producto
        productClient.updateCost(savedItem.getProductCode(), savedItem.getUnitCost());

        // 4️⃣ Actualizar totales de la orden
        BigDecimal newSubtotal = purchaseOrder.getItems().stream()
                .map(PurchaseOrderItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseOrder.setSubtotal(newSubtotal);
        purchaseOrder.setTotalAmount(newSubtotal);
        purchaseOrderRepository.save(purchaseOrder);

        return savedItem;
    }

    public List<PurchaseOrderItem> getAll() {
        return purchaseOrderItemRepository.findAll();
    }

    public PurchaseOrderItem getById(Long id) {
        return purchaseOrderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PurchaseOrderItem not found"));
    }

    @Transactional
    public PurchaseOrderItem addQuantity(Long poItemId, Integer quantity, BigDecimal unitCost) {
        PurchaseOrderItem item = purchaseOrderItemRepository.findById(poItemId)
                .orElseThrow(() -> new RuntimeException("PurchaseOrderItem not found"));

        Long purchaseOrderId = item.getPurchaseOrder().getPurchaseOrderId();

        // Buscar el batch desde msvc-products
        BatchDTO batch = batchClient.getBatchByPurchaseOrderAndProduct(
                purchaseOrderId,
                item.getProductCode()
        );

        if (batch == null) {
            throw new RuntimeException("Batch not found for product " + item.getProductCode()
                    + " in purchase order " + purchaseOrderId);
        }
        System.out.println("batchId = " + batch.getId());

        // Registrar movimiento IN en msvc-products
        AddQuantityRequest request = new AddQuantityRequest(quantity, unitCost);
        stockMovementClient.stockMovementIn(batch.getId(), request);

        // Actualizar el PurchaseOrderItem
        item.setQuantityOrdered(item.getQuantityOrdered() + quantity);
        item.setUnitCost(unitCost);
        item.setTotalCost(item.getUnitCost().multiply(BigDecimal.valueOf(item.getQuantityOrdered())));

        PurchaseOrderItem updated = purchaseOrderItemRepository.save(item);

        // Recalcular totales de la orden
        PurchaseOrder purchaseOrder = updated.getPurchaseOrder();
        BigDecimal newSubtotal = purchaseOrder.getItems().stream()
                .map(PurchaseOrderItem::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseOrder.setSubtotal(newSubtotal);
        purchaseOrder.setTotalAmount(newSubtotal);
        purchaseOrderRepository.save(purchaseOrder);

        return updated;
    }

}
