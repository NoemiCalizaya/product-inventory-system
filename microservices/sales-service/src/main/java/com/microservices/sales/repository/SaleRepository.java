package com.microservices.sales.repository;

import com.microservices.sales.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySalesmanCi(String salesmanCi);
    List<Sale> findByState(String state);
    List<Sale> findBySalesmanCiAndState(String salesmanCi, String state);
} 