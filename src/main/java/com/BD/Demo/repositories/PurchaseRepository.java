package com.BD.Demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BD.Demo.dto.response.BatchResponseDTO;
import com.BD.Demo.dto.response.PurchaseResponseDTO;
import com.BD.Demo.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.batches WHERE p.purchaseId = :purchaseId")
    Optional<Purchase> findByIdWithBatches(@Param("purchaseId") Long purchaseId);

    @Query("SELECT new com.BD.Demo.dto.response.PurchaseResponseDTO(p.purchaseId, su.supplierName, p.dateAcquisition, p.purchaseCost, p.state) " +
        "FROM Purchase p " +
        "JOIN p.supplier su")
    List<PurchaseResponseDTO> findAllPurchases();

}
