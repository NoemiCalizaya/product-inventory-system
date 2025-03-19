package com.BD.Demo.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.dto.request.SaleRequestDTO;
import com.BD.Demo.dto.request.SalesDetailRequestDTO;
import com.BD.Demo.dto.response.SaleResponseDTO;
import com.BD.Demo.dto.response.SalesDTO;
import com.BD.Demo.entities.Batch;
import com.BD.Demo.entities.Sale;
import com.BD.Demo.entities.SalesDetail;
import com.BD.Demo.entities.Salesman;
import com.BD.Demo.repositories.BatchRepository;
import com.BD.Demo.repositories.SaleRepository;
import com.BD.Demo.repositories.SalesDetailRepository;
import com.BD.Demo.repositories.SalesmanRepository;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private SalesDetailRepository salesDetailRepository;

    @Autowired
    private BatchRepository batchRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional(readOnly = true)
    public List<SalesDTO> findAllSales() {
        return saleRepository.findAllSalesWithSalesmanName();
    }

    @Transactional(readOnly = true)
    public Optional<Sale> findById(Long id){
        return saleRepository.findById(id);
    }

    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleRequest) {
        Salesman salesman = salesmanRepository.findByFullName(saleRequest.getSalesmanName())
            .orElseThrow(() -> new RuntimeException("Salesman not found"));
        
        Sale sale = new Sale();
        sale.setSalesman(salesman);
        sale.setSaleDate(LocalDate.now());
        sale.setTotalAmount(BigDecimal.ZERO);
        sale.setState(true);
        sale = saleRepository.save(sale);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SaleRequestDTO.SalesDetailRequestDTO detail : saleRequest.getDetails()) {
            Batch batch = batchRepository.findById(detail.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found"));

            if (batch.getQuantity() < detail.getQuantity()) {
                throw new RuntimeException("Insufficient stock in batch " + batch.getBatchNumber());
            }

            batch.setQuantity(batch.getQuantity() - detail.getQuantity());
            batchRepository.save(batch);

            SalesDetail saleDetail = new SalesDetail();
            saleDetail.setSale(sale);
            saleDetail.setBatch(batch);
            saleDetail.setQuantity(detail.getQuantity());

            BigDecimal unitPrice = detail.getUnitPrice();
            saleDetail.setUnitPrice(detail.getUnitPrice());

            BigDecimal quantity = BigDecimal.valueOf(detail.getQuantity());
            BigDecimal subtotal = quantity.multiply(unitPrice);
            
            saleDetail.setSubtotal(subtotal);
            saleDetail.setState(true);
            salesDetailRepository.save(saleDetail);

            totalAmount = totalAmount.add(subtotal); // Sumar al total de la venta
        }

        sale.setTotalAmount(totalAmount);
        saleRepository.save(sale);

        return new SaleResponseDTO(
            sale.getSaleId(), sale.getSalesman(), sale.getSaleDate(), sale.getTotalAmount(), sale.getState()
        );
    }

    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
