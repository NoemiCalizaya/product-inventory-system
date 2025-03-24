package com.microservices.purchase.service;

import com.microservices.purchase.dto.BatchDTO;
import com.microservices.purchase.dto.PurchaseDTO;
import com.microservices.purchase.dto.PurchaseRequestDTO;
import com.microservices.purchase.dto.PurchaseResponseDTO;
import com.microservices.purchase.entity.Batch;
import com.microservices.purchase.entity.Purchase;
import com.microservices.purchase.exceptions.PurchaseCreationException;
import com.microservices.purchase.client.InventoryClient;
import com.microservices.purchase.repository.PurchaseRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional
    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO requestDTO) {
        try {
            // Validar datos
            validateSalesman(requestDTO.getSalesmanCi());
            validateSupplier(requestDTO.getSupplierId());

            //Servicio de inventario
            PurchaseDTO inventoryPurchaseDTO = new PurchaseDTO();
            inventoryPurchaseDTO.setSalesmanCi(requestDTO.getSalesmanCi());
            inventoryPurchaseDTO.setSupplierId(requestDTO.getSupplierId());
            inventoryPurchaseDTO.setDateAcquisition(LocalDate.now());
            inventoryPurchaseDTO.setState("PENDING");
            inventoryPurchaseDTO.setPurchaseCost(BigDecimal.ZERO);

            ResponseEntity<PurchaseDTO> inventoryResponse;
            try {
                inventoryResponse = inventoryClient.createPurchase(inventoryPurchaseDTO);
                if (!inventoryResponse.getStatusCode().is2xxSuccessful() || inventoryResponse.getBody() == null) {
                    throw new RuntimeException("Error al crear la compra en el servicio de inventario");
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al comunicarse con el servicio de inventario: " + e.getMessage());
            }

            // Servicio de compras
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(inventoryResponse.getBody().getPurchaseId());
            purchase.setSalesmanCi(requestDTO.getSalesmanCi());
            purchase.setSupplierId(requestDTO.getSupplierId());
            purchase.setDateAcquisition(LocalDate.now());
            purchase.setState("PENDING");
            purchase.setPurchaseCost(BigDecimal.ZERO);

            Purchase savedPurchase = purchaseRepository.save(purchase);

            //transacción
            purchaseRepository.flush();

            //lotes en el servicio de inventario
            List<Batch> batches = createBatchesInInventory(savedPurchase.getPurchaseId(), requestDTO.getBatches());

            // Asociar lotes con la compra
            savedPurchase.setBatches(batches);

            // costo total y actualizar la compra
            BigDecimal totalCost = calculateTotalCost(batches);
            savedPurchase.setPurchaseCost(totalCost);

            purchaseRepository.save(savedPurchase);

            return convertToResponseDTO(savedPurchase);
        } catch (Exception e) {
            throw new PurchaseCreationException("Error al crear la compra: " + e.getMessage(), e);
        }
    }


    public PurchaseResponseDTO getPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    private void validateSalesman(String salesmanCi) {
        // Aquí se implementaría la llamada al servicio de usuarios para validar que el vendedor existe
        if (salesmanCi == null || salesmanCi.trim().isEmpty()) {
            throw new RuntimeException("CI del vendedor es requerida");
        }
    }

    private void validateSupplier(Long supplierId) {
        // Aquí se implementaría la llamada al servicio de proveedores
        if (supplierId == null) {
            throw new RuntimeException("ID del proveedor es requerido");
        }
    }

    private void validateBatchData(BatchDTO batchDTO) {
        if (batchDTO.getPurchaseId() == null) {
            throw new RuntimeException("El ID de la compra es requerido");
        }
        if (batchDTO.getProductCod() == null || batchDTO.getProductCod().trim().isEmpty()) {
            throw new RuntimeException("El código del producto es requerido");
        }
        if (batchDTO.getQuantity() == null || batchDTO.getQuantity() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor que cero");
        }
        if (batchDTO.getCostUnit() == null || batchDTO.getCostUnit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El costo unitario debe ser mayor que cero");
        }
        if (batchDTO.getBatchNumber() == null || batchDTO.getBatchNumber().trim().isEmpty()) {
            throw new RuntimeException("El número de lote es requerido");
        }
        if (batchDTO.getState() == null || batchDTO.getState().trim().isEmpty()) {
            throw new RuntimeException("El estado es requerido");
        }
    }

    @Transactional
    private List<Batch> createBatchesInInventory(Long purchaseId, List<BatchDTO> batchDTOs) {
        
        purchaseRepository.flush();

        // Sincronización
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<Batch> createdBatches = new ArrayList<>();
        Purchase purchase = purchaseRepository.findById(purchaseId)
            .orElseThrow(() -> new RuntimeException("No se encontró la compra con ID: " + purchaseId));
        
        for (BatchDTO batchDTO : batchDTOs) {
            try {
                // Creando un nuevo DTO para enviar al servicio de inventario
                BatchDTO inventoryBatchDTO = new BatchDTO();
                BeanUtils.copyProperties(batchDTO, inventoryBatchDTO);
                inventoryBatchDTO.setPurchaseId(purchaseId);
                
                if (inventoryBatchDTO.getProductCod() == null || inventoryBatchDTO.getProductCod().trim().isEmpty()) {
                    throw new RuntimeException("El código del producto es requerido");
                }
                
                if (inventoryBatchDTO.getQuantity() == null || inventoryBatchDTO.getQuantity() <= 0) {
                    throw new RuntimeException("La cantidad debe ser mayor que cero");
                }
                
                if (inventoryBatchDTO.getCostUnit() == null || inventoryBatchDTO.getCostUnit().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new RuntimeException("El costo unitario debe ser mayor que cero");
                }
                
                // Estableciendo valores por defecto si no están presentes
                if (inventoryBatchDTO.getBatchNumber() == null) {
                    inventoryBatchDTO.setBatchNumber(generateUniqueBatchNumber());
                }
                if (inventoryBatchDTO.getState() == null) {
                    inventoryBatchDTO.setState("ACTIVE");
                }
                if (inventoryBatchDTO.getAvailableQuantity() == null) {
                    inventoryBatchDTO.setAvailableQuantity(inventoryBatchDTO.getQuantity());
                }
                if (inventoryBatchDTO.getManufacturingDate() == null) {
                    inventoryBatchDTO.setManufacturingDate(LocalDate.now());
                }

                // Validando datos antes de enviar
                validateBatchData(inventoryBatchDTO);

                // Creando el lote en el servicio de inventario
                ResponseEntity<BatchDTO> response = inventoryClient.createBatch(inventoryBatchDTO);
                
                if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                    throw new RuntimeException("Error al crear el lote en el servicio de inventario");
                }
                
                // Creando el lote en el servicio de compras
                Batch batch = new Batch();
                BeanUtils.copyProperties(response.getBody(), batch);
                batch.setPurchase(purchase);
                createdBatches.add(batch);
                
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el lote: " + e.getMessage());
            }
        }
        
        return createdBatches;
    }

    // Método para generar un número único de lote
    private String generateUniqueBatchNumber() {
        return "BATCH-" + System.currentTimeMillis();
    }
        

    private BigDecimal calculateTotalCost(List<Batch> batches) {
        return batches.stream()
                .map(batch -> batch.getCostUnit().multiply(new BigDecimal(batch.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PurchaseResponseDTO convertToResponseDTO(Purchase purchase) {
        PurchaseResponseDTO responseDTO = new PurchaseResponseDTO();
        BeanUtils.copyProperties(purchase, responseDTO);
        
        List<BatchDTO> batchDTOs = purchase.getBatches().stream()
                .map(batch -> {
                    BatchDTO batchDTO = new BatchDTO();
                    BeanUtils.copyProperties(batch, batchDTO);
                    return batchDTO;
                })
                .collect(Collectors.toList());
        
        responseDTO.setBatches(batchDTOs);
        return responseDTO;
    }

    private void validatePurchaseStateTransition(String currentState, String newState) {
        if (currentState.equals("COMPLETED") || currentState.equals("REJECTED")) {
            throw new RuntimeException("No se puede cambiar el estado de una compra " + currentState);
        }
        if (currentState.equals("PENDING") && !newState.equals("APPROVED") && !newState.equals("REJECTED")) {
            throw new RuntimeException("Una compra pendiente solo puede ser aprobada o rechazada");
        }
        if (currentState.equals("APPROVED") && !newState.equals("COMPLETED")) {
            throw new RuntimeException("Una compra aprobada solo puede ser completada");
        }
    }
}