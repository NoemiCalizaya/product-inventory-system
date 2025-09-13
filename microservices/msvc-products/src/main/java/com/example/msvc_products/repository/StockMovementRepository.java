package com.example.msvc_products.repository;

import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    Optional<StockMovement> findTopByBatchOrderByCreatedAtDesc(Batch batch);

    List<StockMovement> findByBatchOrderByCreatedAtAsc(Batch batch);
}
