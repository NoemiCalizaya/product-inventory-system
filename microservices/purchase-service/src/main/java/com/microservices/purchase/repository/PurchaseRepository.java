package com.microservices.purchase.repository;

import com.microservices.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findBySalesmanCi(String salesmanCi);
    List<Purchase> findByState(String state);
    List<Purchase> findBySalesmanCiAndState(String salesmanCi, String state);
} 