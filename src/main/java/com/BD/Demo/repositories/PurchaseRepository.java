package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    
}
