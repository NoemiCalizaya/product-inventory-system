package com.microservices.inventory.repository;

import com.microservices.inventory.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByProductCod(String productCod);
    
    List<Batch> findByState(String state);
    
    @Query("SELECT b FROM Batch b WHERE b.availableQuantity > 0 AND b.state = 'ACTIVE' ORDER BY b.expirationDate ASC")
    List<Batch> findAvailableBatches();
    
    @Query("SELECT b FROM Batch b WHERE b.batchId = :batchId AND b.availableQuantity >= :quantity")
    Batch findBatchWithSufficientStock(Long batchId, Integer quantity);
} 