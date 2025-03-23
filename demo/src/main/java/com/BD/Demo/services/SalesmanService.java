package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.BD.Demo.entities.Salesman;
import com.BD.Demo.repositories.SalesmanRepository;

@Service
public class SalesmanService {
    
    private final SalesmanRepository salesmanRepository;

    public SalesmanService(SalesmanRepository salesmanRepository){
        this.salesmanRepository = salesmanRepository;
    }

    @Transactional(readOnly = true)
    public List<Salesman> findAll() {
        return salesmanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Salesman> findById(String ci) {
        return salesmanRepository.findById(ci);
    }

    public Salesman saveSalesman(Salesman salesman) {
        return salesmanRepository.save(salesman);
    }

<<<<<<< HEAD
    public Optional<Salesman> updateSalesman(String ci, Salesman salesman) {
        return salesmanRepository.findById(ci)
                .map(existingSalesman -> {
                    salesman.setCi(ci);
                    return salesmanRepository.save(salesman);
                });
=======
    public Salesman updateSalesman(Salesman salesman) {
        return salesmanRepository.save(salesman);
>>>>>>> ada7324c114459081cccb2b4f6e2e33eca28c2bb
    }

    public void deleteSalesman(String ci) {
        salesmanRepository.deleteById(ci);
    }
}
