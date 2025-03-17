package com.BD.Demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long>{
    
}
