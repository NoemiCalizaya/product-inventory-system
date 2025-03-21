package com.microservices.user.service;

import com.microservices.user.dto.CreateSalesmanRequest;
import com.microservices.user.dto.SalesmanResponse;
import com.microservices.user.entity.Salesman;
import com.microservices.user.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesmanService {
    
    @Autowired
    private SalesmanRepository salesmanRepository;
    
    public SalesmanResponse createSalesman(CreateSalesmanRequest request) {
        if (salesmanRepository.existsById(request.getCi())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CI already exists");
        }
        if (salesmanRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        
        Salesman salesman = new Salesman();
        salesman.setCi(request.getCi());
        salesman.setName(request.getName());
        salesman.setEmail(request.getEmail());
        salesman.setState("ACTIVE"); // Estado por defecto
        
        salesman = salesmanRepository.save(salesman);
        return convertToResponse(salesman);
    }
    
    public SalesmanResponse getSalesmanByCi(String ci) {
        Salesman salesman = salesmanRepository.findById(ci)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Salesman not found"));
        return convertToResponse(salesman);
    }
    
    public List<SalesmanResponse> getAllSalesmen() {
        return salesmanRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<SalesmanResponse> getSalesmenByState(String state) {
        return salesmanRepository.findByState(state).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public SalesmanResponse updateSalesmanState(String ci, String newState) {
        Salesman salesman = salesmanRepository.findById(ci)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Salesman not found"));
        
        salesman.setState(newState);
        salesman = salesmanRepository.save(salesman);
        return convertToResponse(salesman);
    }
    
    private SalesmanResponse convertToResponse(Salesman salesman) {
        return new SalesmanResponse(
                salesman.getCi(),
                salesman.getName(),
                salesman.getEmail(),
                salesman.getState()
        );
    }
} 