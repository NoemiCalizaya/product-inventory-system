package com.BD.Demo.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.dto.request.SaleRequestDTO;
import com.BD.Demo.dto.response.ProductResponseDTO;
import com.BD.Demo.dto.response.ProfitDetailDTO;
import com.BD.Demo.dto.response.SaleResponseDTO;
import com.BD.Demo.dto.response.SalesDTO;
import com.BD.Demo.dto.response.TotalProfitDTO;
import com.BD.Demo.entities.Batch;
import com.BD.Demo.entities.Sale;
import com.BD.Demo.entities.SalesDetail;
import com.BD.Demo.entities.Salesman;
import com.BD.Demo.exceptions.BatchNotFoundException;
import com.BD.Demo.exceptions.InsufficientStockException;
import com.BD.Demo.exceptions.SalesmanNotFoundException;
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
    public SaleResponseDTO create(SaleRequestDTO saleRequest) {
        Optional<Salesman> currentSalesman = salesmanRepository.findByCi(saleRequest.getCi());

        if (!currentSalesman.isPresent()) {
            throw new SalesmanNotFoundException("No existe el vendedor seleccionado.");
        }

        Sale sale = new Sale();
        sale.setSalesman(currentSalesman.get());
        sale.setSaleDate(LocalDate.now());
        sale.setTotalAmount(BigDecimal.ZERO);
        sale.setState(true);

        saleRepository.save(sale);
    
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Batch> updatedBatches = new ArrayList<>();
        List<SalesDetail> salesDetails = new ArrayList<>();
    
        for (SaleRequestDTO.SalesDetailRequestDTO detail : saleRequest.getDetails()) {
            Batch batch = batchRepository.findById(detail.getBatchId())
                .orElseThrow(() -> new BatchNotFoundException("No se encuentra el lote."));
            
            if (batch.getQuantity() < detail.getQuantity()) {
                throw new InsufficientStockException("Stock insuficiente en el lote especificado.");
            }
    
            int quantityToSell = detail.getQuantity();
            BigDecimal unitPrice = detail.getUnitPrice();
    
            batch.setQuantity(batch.getQuantity() - quantityToSell);
            updatedBatches.add(batch);
    
            SalesDetail saleDetail = new SalesDetail();
            saleDetail.setSale(sale);
            saleDetail.setBatch(batch);
            saleDetail.setQuantity(quantityToSell);
            saleDetail.setUnitPrice(unitPrice);
            saleDetail.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(quantityToSell)));
            saleDetail.setState(true);
            salesDetails.add(saleDetail);
    
            totalAmount = totalAmount.add(saleDetail.getSubtotal());
        }
    
        batchRepository.saveAll(updatedBatches);
        salesDetailRepository.saveAll(salesDetails);
    
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

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getTopSellingProducts(int topX) {
        return salesDetailRepository.findTopSellingProducts(PageRequest.of(0, topX));
    }

    public Map<String, Object> calculateTotalProfit(LocalDate startDate, LocalDate endDate) {
        List<SalesDetail> salesDetails = salesDetailRepository.findSalesByDateRange(startDate, endDate);

        BigDecimal totalProfit = BigDecimal.ZERO;
        List<ProfitDetailDTO> profitDetails = new ArrayList<>();

        for (SalesDetail detail : salesDetails) {
            BigDecimal totalSale = detail.getSale().getTotalAmount();
            totalProfit = totalProfit.add(totalSale);

            profitDetails.add(new ProfitDetailDTO(
                    detail.getSale().getSaleDate(),
                    detail.getBatch().getProduct().getProductName(),
                    detail.getBatch().getProduct().getSalePrice(),
                    detail.getQuantity(),
                    totalSale
            ));
        }

        // Usamos un MAP para devolver la lista de detalles y el total separado
        Map<String, Object> response = new HashMap<>();
        response.put("details", profitDetails);
        response.put("totalProfit", new TotalProfitDTO(totalProfit));

        return response;
    }
    
}
