package com.BD.Demo.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.dto.request.BatchRequestDTO;
import com.BD.Demo.dto.request.PurchaseRequestDTO;
import com.BD.Demo.dto.response.BatchResponseDTO;
import com.BD.Demo.dto.response.PurchaseResponseDTO;
import com.BD.Demo.entities.Batch;
import com.BD.Demo.entities.Product;
import com.BD.Demo.entities.Purchase;
import com.BD.Demo.repositories.BatchRepository;
import com.BD.Demo.repositories.ProductRepository;
import com.BD.Demo.repositories.PurchaseRepository;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional(readOnly = true)
    public List<PurchaseResponseDTO> findAllPurchasesWithBatches() {
        List<PurchaseResponseDTO> purchases = purchaseRepository.findAllPurchases();
        
        for (PurchaseResponseDTO purchase : purchases) {
            List<BatchResponseDTO> batches = batchRepository.findBatchesByPurchaseId(purchase.getPurchaseId());
            purchase.setBatches(batches);  // Agrega los lotes a la compra
        }
        
        return purchases;
    }    

    @Transactional(readOnly = true)
    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO purchaseRequest) {

        Purchase purchase = new Purchase();
        purchase.setSupplier(purchaseRequest.getSupplier());
        purchase.setDateAcquisition(purchaseRequest.getDateAcquisition());
        purchase.setPurchaseCost(purchaseRequest.getPurchaseCost());
        purchase.setState(true);

        purchase = purchaseRepository.save(purchase);

        // Crear lotes asociados a la compra
        for (BatchRequestDTO batchRequest : purchaseRequest.getBatches()) {
            Product product = productRepository.findById(batchRequest.getProductCod())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Batch batch = new Batch();
            batch.setProduct(product);
            batch.setPurchase(purchase);
            batch.setBatchNumber(batchRequest.getBatchNumber());
            batch.setExpirationDate(batchRequest.getExpirationDate());
            batch.setManufacturingDate(batchRequest.getManufacturingDate());
            batch.setQuantity(batchRequest.getQuantity());
            batch.setCostUnit(batchRequest.getCostUnit());
            batch.setState(true);  // Lote activo por defecto

            batchRepository.save(batch);

            BigDecimal profitMargin = product.getProfitMargin();
            if (profitMargin.compareTo(BigDecimal.ONE) > 0) { 
                profitMargin = profitMargin.divide(BigDecimal.valueOf(100), MathContext.DECIMAL64); 
            }
            BigDecimal salePrice = batch.getCostUnit().multiply(profitMargin.add(BigDecimal.ONE));
            product.setSalePrice(salePrice);

        }

        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setPurchaseId(purchase.getPurchaseId());
        response.setDateAcquisition(purchase.getDateAcquisition());
        response.setPurchaseCost(purchase.getPurchaseCost());

        return response;
    }

    @Transactional
    public PurchaseResponseDTO updatePurchase(Long purchaseId, PurchaseRequestDTO purchaseRequest) {
        // Buscar la compra existente
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        // Actualizar datos de la compra
        purchase.setSupplier(purchaseRequest.getSupplier());
        purchase.setDateAcquisition(purchaseRequest.getDateAcquisition());
        purchase.setPurchaseCost(purchaseRequest.getPurchaseCost());

        purchaseRepository.save(purchase); // Guardar cambios en la compra

        for (BatchRequestDTO batchRequest : purchaseRequest.getBatches()) {
            Optional<Batch> existingBatch = batchRepository.findByBatchNumberAndPurchaseId(
                    batchRequest.getBatchNumber(), purchaseId);

            if (existingBatch.isPresent()) {
                Batch batch = existingBatch.get();
                batch.setExpirationDate(batchRequest.getExpirationDate());
                batch.setManufacturingDate(batchRequest.getManufacturingDate());
                batch.setQuantity(batchRequest.getQuantity());
                batch.setCostUnit(batchRequest.getCostUnit());

                batchRepository.save(batch);
            }
        }

        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setPurchaseId(purchase.getPurchaseId());
        response.setDateAcquisition(purchase.getDateAcquisition());
        response.setPurchaseCost(purchase.getPurchaseCost());

        List<BatchResponseDTO> updatedBatches = batchRepository.findBatchesByPurchaseId(purchase.getPurchaseId());
        response.setBatches(updatedBatches);

        return response;
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
