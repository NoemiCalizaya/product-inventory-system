package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    
}
