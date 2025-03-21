package com.BD.Demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BD.Demo.dto.response.BatchResponseDTO;
import com.BD.Demo.entities.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long>{
    @Query("SELECT new com.BD.Demo.dto.response.BatchResponseDTO(b.batchId, b.product.productName, b.batchNumber, b.expirationDate, b.manufacturingDate, b.quantity, b.costUnit) " +
        "FROM Batch b WHERE b.purchase.purchaseId = :purchaseId")
    List<BatchResponseDTO> findBatchesByPurchaseId(@Param("purchaseId") Long purchaseId);

    @Query("SELECT b FROM Batch b WHERE b.batchNumber = :batchNumber AND b.purchase.purchaseId = :purchaseId")
    Optional<Batch> findByBatchNumberAndPurchaseId(@Param("batchNumber") String batchNumber, @Param("purchaseId") Long purchaseId);
    
    @Query("SELECT b FROM Batch b WHERE b.product.id = :productId AND b.quantity > 0 ORDER BY b.expirationDate ASC")
    List<Batch> findAvailableBatchesOrderedByDate(@Param("productId") Long productId);

    @Query("SELECT b FROM Batch b WHERE b.quantity < :threshold")
    List<Batch> findLowStockProducts(@Param("threshold") int threshold);
}
