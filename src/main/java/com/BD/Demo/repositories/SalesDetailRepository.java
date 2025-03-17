package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.SalesDetail;

@Repository
public interface SalesDetailRepository extends JpaRepository<SalesDetail, Long>{
    
}
