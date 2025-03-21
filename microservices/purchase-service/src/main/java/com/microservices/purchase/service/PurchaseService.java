package com.microservices.purchase.service;

import com.microservices.purchase.client.InventoryClient;
import com.microservices.purchase.dto.BatchDTO;
import com.microservices.purchase.dto.PurchaseRequestDTO;
import com.microservices.purchase.dto.PurchaseResponseDTO;
import com.microservices.purchase.entity.Batch;
import com.microservices.purchase.entity.Purchase;
import com.microservices.purchase.repository.PurchaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        // Validar que el vendedor exista (esto se haría con un cliente Feign al user-service)
        validateSalesman(requestDTO.getSalesmanCi());
        
        // Validar que el proveedor exista (esto se haría con un cliente Feign)
        validateSupplier(requestDTO.getSupplierId());

        Purchase purchase = new Purchase();
        purchase.setSalesmanCi(requestDTO.getSalesmanCi());
        purchase.setSupplierId(requestDTO.getSupplierId());
        purchase.setDateAcquisition(LocalDate.now());
        purchase.setState("PENDING"); // Estados posibles: PENDING, APPROVED, REJECTED, COMPLETED

        // Guardar la compra primero para obtener el ID
        purchase = purchaseRepository.save(purchase);

        // Crear los lotes en el inventario
        List<Batch> batches = createBatchesInInventory(purchase.getPurchaseId(), requestDTO.getBatches());
        purchase.setBatches(batches);

        // Calcular costo total de la compra
        BigDecimal totalCost = calculateTotalCost(batches);

        purchase.setPurchaseCost(totalCost);

        purchase = purchaseRepository.save(purchase);
        
        return convertToResponseDTO(purchase);
    }

    public List<PurchaseResponseDTO> getPurchasesBySalesman(String salesmanCi) {
        validateSalesman(salesmanCi);
        return purchaseRepository.findBySalesmanCi(salesmanCi).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PurchaseResponseDTO> getPurchasesBySalesmanAndState(String salesmanCi, String state) {
        validateSalesman(salesmanCi);
        return purchaseRepository.findBySalesmanCiAndState(salesmanCi, state).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PurchaseResponseDTO updatePurchaseState(Long purchaseId, String newState) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        
        validatePurchaseStateTransition(purchase.getState(), newState);
        purchase.setState(newState);
        
        purchase = purchaseRepository.save(purchase);
        return convertToResponseDTO(purchase);
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
        if (batchDTO.getQuantity() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        if (batchDTO.getCostUnit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El costo unitario debe ser mayor a 0");
        }
        if (batchDTO.getExpirationDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de vencimiento no puede ser anterior a la fecha actual");
        }
    }

    private void validatePurchaseStateTransition(String currentState, String newState) {
        // Implementar lógica de transición de estados
        // Ejemplo: PENDING -> APPROVED -> COMPLETED
        //         PENDING -> REJECTED
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

    private List<Batch> createBatchesInInventory(Long purchaseId, List<BatchDTO> batchDTOs) {
        return batchDTOs.stream()
                .map(batchDTO -> {
                    // Crear un nuevo BatchDTO para enviar a inventory-service
                    BatchDTO inventoryBatchDTO = new BatchDTO();
                    BeanUtils.copyProperties(batchDTO, inventoryBatchDTO);
                    inventoryBatchDTO.setPurchaseId(purchaseId); // Asegurar que el purchaseId se setea correctamente
    
                    // Llamada a InventoryClient
                    ResponseEntity<BatchDTO> response = inventoryClient.createBatch(inventoryBatchDTO);
                    if (response.getBody() == null) {
                        throw new RuntimeException("Error al crear el lote en inventario");
                    }
    
                    // Convertir la respuesta a entidad Batch
                    Batch batch = new Batch();
                    BeanUtils.copyProperties(response.getBody(), batch);
                    return batch;
                })
                .collect(Collectors.toList());
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
}