package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.entities.SalesDetail;
import com.BD.Demo.repositories.SalesDetailRepository;


@Service
public class SalesDetailService {
    private final SalesDetailRepository salesDetailRepository;

    public SalesDetailService(SalesDetailRepository salesDetailRepository) {
        this.salesDetailRepository = salesDetailRepository;
    }

    @Transactional(readOnly = true)
    public List<SalesDetail> findAll(){
        return salesDetailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<SalesDetail> findById(Long id) {
        return salesDetailRepository.findById(id);
    }

    public SalesDetail saveSalesDetail(SalesDetail salesDetail) {
        return salesDetailRepository.save(salesDetail);
    }

    public SalesDetail updateSalesDetail(SalesDetail salesDetail) {
        return salesDetailRepository.save(salesDetail);
    }

    public void deleteSalesDetail(Long id) {
        salesDetailRepository.deleteById(id);
    }
}
