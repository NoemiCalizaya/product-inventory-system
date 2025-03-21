package com.microservices.inventory.service;

import com.microservices.inventory.dto.BatchDTO;
import com.microservices.inventory.dto.StockUpdateRequestDTO;
import com.microservices.inventory.dto.BatchStockUpdate;
import com.microservices.inventory.entity.Batch;
import com.microservices.inventory.repository.BatchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

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
                if (batch.getAvailableQuantity() < update.getQuantity()) {
                    throw new RuntimeException("Stock insuficiente en el lote: " + update.getBatchId());
                }
                batch.setAvailableQuantity(batch.getAvailableQuantity() - update.getQuantity());
            } else if ("INCREASE".equals(update.getOperation())) {
                if (batch.getAvailableQuantity() + update.getQuantity() > batch.getQuantity()) {
                    throw new RuntimeException("La cantidad excede el máximo del lote: " + update.getBatchId());
                }
                batch.setAvailableQuantity(batch.getAvailableQuantity() + update.getQuantity());
            }

            // Actualizar estado si es necesario
            if (batch.getAvailableQuantity() == 0) {
                batch.setState("SOLD_OUT");
            } else if (batch.getAvailableQuantity() > 0 && "SOLD_OUT".equals(batch.getState())) {
                batch.setState("ACTIVE");
            }

            batchRepository.save(batch);
        });
    }

    @Transactional
    public BatchDTO createBatch(BatchDTO batchDTO) {
        Batch batch = convertToEntity(batchDTO);
        batch.setAvailableQuantity(batch.getQuantity()); // Inicialmente, la cantidad disponible es igual a la cantidad total
        batch.setState("ACTIVE");
        batch = batchRepository.save(batch);
        return convertToDTO(batch);
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