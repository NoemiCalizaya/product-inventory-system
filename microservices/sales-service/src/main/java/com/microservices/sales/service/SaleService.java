package com.microservices.sales.service;

import com.microservices.sales.client.InventoryClient;
import com.microservices.sales.client.ProductClient;
import com.microservices.sales.dto.*;
import com.microservices.sales.entity.*;
import com.microservices.sales.exceptions.SalesmanNotFoundException;
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

    @Autowired
    private ProductClient productClient;

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO requestDTO) {
        try {
            validateSalesman(requestDTO.getSalesmanCi());
            
            // Validate stock availability for all products
            validateStockAvailability(requestDTO.getDetails());  // Updated to match the new field name
    
            Sale sale = new Sale();
            sale.setSalesmanCi(requestDTO.getSalesmanCi());
            sale.setSaleDate(LocalDate.now());
            sale.setState("PENDING");
    
            List<SaleDetail> details = createSaleDetails(sale, requestDTO.getDetails());  // Updated to match the new field name
            BigDecimal totalAmount = calculateTotalAmount(details);
    
            sale.setTotalAmount(totalAmount);
            sale.setSaleDetails(details);
    
            sale = saleRepository.save(sale);
            
            // Update inventory stock after saving the sale
            updateInventoryStock(sale.getSaleDetails());
            
            return convertToResponseDTO(sale);
        } catch (Exception e) {
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    private void validateStockAvailability(List<SaleDetailRequestDTO> details) {
        if (details == null || details.isEmpty()) {
            throw new RuntimeException("Sale details cannot be empty");
        }
        
        for (SaleDetailRequestDTO detail : details) {
            if (detail.getProductCod() == null || detail.getQuantity() == null) {
                throw new RuntimeException("Product code and quantity are required");
            }
            
            try {
                ResponseEntity<Long> batchResponse = inventoryClient.findBatchByProductCode(detail.getProductCod());
                if (!batchResponse.getStatusCode().is2xxSuccessful() || batchResponse.getBody() == null) {
                    throw new RuntimeException("No available batch found for product: " + detail.getProductCod());
                }
                Long batchId = batchResponse.getBody();
                
                ResponseEntity<Boolean> response = inventoryClient.checkBatchAvailability(
                    batchId, 
                    detail.getQuantity()
                );
                
                if (!Boolean.TRUE.equals(response.getBody())) {
                    throw new RuntimeException("Insufficient stock for product: " + detail.getProductCod());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error validating stock for product " + detail.getProductCod() + ": " + e.getMessage());
            }
        }
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

    private void updateInventoryStock(List<SaleDetail> saleDetails) {
        List<BatchStockUpdate> updates = saleDetails.stream()
            .filter(detail -> "PENDING".equals(detail.getState()))
            .map(detail -> {
                BatchStockUpdate update = new BatchStockUpdate();
                update.setBatchId(detail.getBatchId());
                update.setQuantity(detail.getQuantity());
                update.setOperation("DECREASE");
                return update;
            })
            .collect(Collectors.toList());

        if (!updates.isEmpty()) {
            StockUpdateRequest request = new StockUpdateRequest();
            request.setUpdates(updates);
            inventoryClient.updateBatchStock(request);
        }
    }

    public List<SaleResponseDTO> getSalesBySalesman(String salesmanCi) {
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
        // TODO: Implementar validación del vendedor cuando el servicio de usuarios esté disponible
        if (salesmanCi == null || salesmanCi.trim().isEmpty()) {
            throw new SalesmanNotFoundException("El CI del vendedor es requerido");
        }
    }

    private void validateSaleStateTransition(String currentState, String newState) {
        if (currentState.equals(newState)) {
            return;
        }
        
        switch (currentState) {
            case "PENDING":
                if (!newState.equals("COMPLETED") && !newState.equals("CANCELLED")) {
                    throw new RuntimeException("Transición de estado inválida");
                }
                break;
            case "COMPLETED":
            case "CANCELLED":
                throw new RuntimeException("No se puede cambiar el estado de una venta " + currentState);
            default:
                throw new RuntimeException("Estado de venta inválido");
        }
    }

    private List<SaleDetail> createSaleDetails(Sale sale, List<SaleDetailRequestDTO> detailsDTO) {
        List<SaleDetail> details = new ArrayList<>();
        for (SaleDetailRequestDTO detailDTO : detailsDTO) {
            ResponseEntity<Long> batchResponse = inventoryClient.findBatchByProductCode(detailDTO.getProductCod());
            if (!batchResponse.getStatusCode().is2xxSuccessful() || batchResponse.getBody() == null) {
                throw new RuntimeException("No se encontró un lote disponible para el producto: " + detailDTO.getProductCod());
            }
            Long batchId = batchResponse.getBody();
            
            ResponseEntity<ProductPriceDTO> priceResponse = productClient.getProductPrice(batchId);
            if (!priceResponse.getStatusCode().is2xxSuccessful() || priceResponse.getBody() == null) {
                throw new RuntimeException("No se pudo obtener el precio del producto");
            }
            
            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setBatchId(batchId);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(priceResponse.getBody().getPrice());
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
