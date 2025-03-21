package com.microservices.sales.service;

import com.microservices.sales.client.InventoryClient;
import com.microservices.sales.dto.*;
import com.microservices.sales.entity.*;
import com.microservices.sales.repository.SaleRepository;
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
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO requestDTO) {
        // Validar que el vendedor exista
        validateSalesman(requestDTO.getSalesmanCi());
        
        // Validar disponibilidad de stock para todos los productos
        validateStockAvailability(requestDTO.getDetails());

        Sale sale = new Sale();
        sale.setSalesmanCi(requestDTO.getSalesmanCi());
        sale.setSaleDate(LocalDate.now());
        sale.setState("PENDING");

        List<SaleDetail> details = createSaleDetails(sale, requestDTO.getDetails());
        BigDecimal totalAmount = calculateTotalAmount(details);

        sale.setTotalAmount(totalAmount);
        sale.setSaleDetails(details);

        sale = saleRepository.save(sale);
        
        return convertToResponseDTO(sale);
    }

    @Transactional
    public SaleResponseDTO updateSaleState(Long saleId, String newState) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        
        validateSaleStateTransition(sale.getState(), newState);
        
        if (newState.equals("COMPLETED")) {
            // Actualizar inventario
            updateInventoryStock(sale.getSaleDetails());
        }
        
        sale.setState(newState);
        sale = saleRepository.save(sale);
        
        return convertToResponseDTO(sale);
    }

    private void validateStockAvailability(List<SaleDetailRequestDTO> details) {
        for (SaleDetailRequestDTO detail : details) {
            ResponseEntity<Boolean> response = inventoryClient.checkBatchAvailability(
                detail.getBatchId(), 
                detail.getQuantity()
            );
            
            if (!Boolean.TRUE.equals(response.getBody())) {
                throw new RuntimeException("Stock insuficiente para el lote: " + detail.getBatchId());
            }
        }
    }

    private void updateInventoryStock(List<SaleDetail> saleDetails) {
        List<BatchStockUpdate> updates = saleDetails.stream()
            .map(detail -> new BatchStockUpdate(
                detail.getBatchId(),
                detail.getQuantity(),
                "DECREASE"
            ))
            .collect(Collectors.toList());

        StockUpdateRequest updateRequest = new StockUpdateRequest();
        updateRequest.setUpdates(updates);
        inventoryClient.updateBatchStock(updateRequest);
    }

    public List<SaleResponseDTO> getSalesBySalesman(String salesmanCi) {
        validateSalesman(salesmanCi);
        return saleRepository.findBySalesmanCi(salesmanCi).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public SaleResponseDTO getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    private void validateSalesman(String salesmanCi) {
        if (salesmanCi == null || salesmanCi.trim().isEmpty()) {
            throw new RuntimeException("CI del vendedor es requerida");
        }
        // Aquí se implementaría la llamada al servicio de usuarios
    }

    private void validateBatchAvailability(Long batchId, Integer requestedQuantity) {
        // Aquí se implementaría la llamada al servicio de inventario para validar stock
        if (batchId == null || requestedQuantity <= 0) {
            throw new RuntimeException("Datos del lote inválidos");
        }
    }

    private BigDecimal getBatchUnitPrice(Long batchId) {
        // Aquí se implementaría la llamada al servicio de productos/inventario
        // para obtener el precio de venta del producto
        return BigDecimal.TEN; // Valor de ejemplo
    }

    private void validateSaleStateTransition(String currentState, String newState) {
        if (currentState.equals("CANCELLED")) {
            throw new RuntimeException("No se puede modificar una venta cancelada");
        }
        if (currentState.equals("COMPLETED")) {
            throw new RuntimeException("No se puede modificar una venta completada");
        }
        if (!newState.equals("COMPLETED") && !newState.equals("CANCELLED")) {
            throw new RuntimeException("Estado de venta inválido");
        }
    }

    private void updateBatchesStock(List<SaleDetail> saleDetails) {
        // Aquí se implementaría la llamada al servicio de inventario
        // para actualizar el stock de los lotes vendidos
    }

    private List<SaleDetail> createSaleDetails(Sale sale, List<SaleDetailRequestDTO> detailsDTO) {
        List<SaleDetail> details = new ArrayList<>();
        
        for (SaleDetailRequestDTO detailDTO : detailsDTO) {
            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setBatchId(detailDTO.getBatchId());
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(getBatchUnitPrice(detailDTO.getBatchId()));
            detail.setSubtotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
            detail.setState("PENDING");
            details.add(detail);
        }
        
        return details;
    }

    private BigDecimal calculateTotalAmount(List<SaleDetail> details) {
        return details.stream()
                .map(SaleDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private SaleResponseDTO convertToResponseDTO(Sale sale) {
        SaleResponseDTO responseDTO = new SaleResponseDTO();
        BeanUtils.copyProperties(sale, responseDTO);
        
        List<SaleDetailDTO> detailDTOs = sale.getSaleDetails().stream()
                .map(detail -> {
                    SaleDetailDTO detailDTO = new SaleDetailDTO();
                    BeanUtils.copyProperties(detail, detailDTO);
                    return detailDTO;
                })
                .collect(Collectors.toList());
        
        responseDTO.setDetails(detailDTOs);
        return responseDTO;
    }
} 