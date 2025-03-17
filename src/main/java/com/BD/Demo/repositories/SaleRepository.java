package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
}
