package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.entities.Sale;
import com.BD.Demo.repositories.SaleRepository;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional(readOnly = true)
    public List<Sale> findAll(){
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sale> findById(Long id){
        return saleRepository.findById(id);
    }

    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
