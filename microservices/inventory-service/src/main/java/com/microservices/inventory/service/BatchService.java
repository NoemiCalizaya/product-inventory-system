package com.microservices.inventory.service;

import com.microservices.inventory.dto.BatchDTO;
import com.microservices.inventory.dto.StockUpdateRequestDTO;
import com.microservices.inventory.dto.BatchStockUpdate;
import com.microservices.inventory.entity.Batch;
import com.microservices.inventory.entity.Purchase;
import com.microservices.inventory.repository.BatchRepository;
import com.microservices.inventory.repository.PurchaseRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public BatchDTO getBatchById(Long batchId) {
        return batchRepository.findById(batchId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<BatchDTO> getAvailableBatches() {
        return batchRepository.findAvailableBatches().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean checkBatchAvailability(Long batchId, Integer quantity) {
        Batch batch = batchRepository.findBatchWithSufficientStock(batchId, quantity);
        return batch != null;
    }

    @Transactional
    public void updateBatchStock(StockUpdateRequestDTO updateRequest) {
        updateRequest.getUpdates().forEach(update -> {
            Batch batch = batchRepository.findById(update.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Lote no encontrado: " + update.getBatchId()));

            if ("DECREASE".equals(update.getOperation())) {
                if (batch.getQuantity() < update.getQuantity() || 
                    batch.getAvailableQuantity() < update.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente en el lote: " + update.getBatchId());
                }
                // Disminuir ambas cantidades
                batch.setQuantity(batch.getQuantity() - update.getQuantity());
                batch.setAvailableQuantity(batch.getAvailableQuantity() - update.getQuantity());
                
                // Actualizar estado del lote si es necesario
                if (batch.getAvailableQuantity() == 0) {
                    batch.setState("SOLD_OUT");
                }
            } else if ("INCREASE".equals(update.getOperation())) {
                // Incrementar stock
                batch.setQuantity(batch.getQuantity() + update.getQuantity());
                batch.setAvailableQuantity(batch.getAvailableQuantity() + update.getQuantity());
            } else {
                throw new RuntimeException("Operación no válida para el lote: " + update.getBatchId());
            }

            batchRepository.save(batch);
        });
    }

    @Transactional
    public BatchDTO createBatch(BatchDTO batchDTO) {
        try {
            // Validaciones básicas
            if (batchDTO == null) {
                throw new RuntimeException("El DTO del lote no puede ser nulo");
            }
            if (batchDTO.getPurchaseId() == null) {
                throw new RuntimeException("El ID de la compra es requerido");
            }

            // Verificar si la compra existe
            Purchase purchase = purchaseRepository.findById(batchDTO.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("La compra con ID " + batchDTO.getPurchaseId() + " no existe."));

            // Validar el estado de la compra
            if (!"PENDING".equals(purchase.getState()) && !"ACTIVE".equals(purchase.getState())) {
                throw new RuntimeException("La compra no está en un estado válido para agregar lotes.");
            }

            // Validaciones adicionales
            if (batchDTO.getQuantity() == null || batchDTO.getQuantity() <= 0) {
                throw new RuntimeException("La cantidad del lote debe ser mayor que cero.");
            }

            if (batchDTO.getAvailableQuantity() == null) {
                batchDTO.setAvailableQuantity(batchDTO.getQuantity());
            }

            if (batchDTO.getState() == null) {
                batchDTO.setState("ACTIVE");
            }

            // Convertir DTO a entidad
            Batch batch = convertToEntity(batchDTO);

            // Guardar el lote
            batch = batchRepository.save(batch);

            // Convertir entidad a DTO para la respuesta
            return convertToDTO(batch);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el lote: " + e.getMessage());
        }
    }

    public List<BatchDTO> getBatchesByProduct(String productCod) {
        return batchRepository.findByProductCod(productCod).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BatchDTO convertToDTO(Batch batch) {
        BatchDTO dto = new BatchDTO();
        BeanUtils.copyProperties(batch, dto);
        return dto;
    }

    private Batch convertToEntity(BatchDTO dto) {
        Batch batch = new Batch();
        BeanUtils.copyProperties(dto, batch);
        return batch;
    }
}
