package com.example.stock_microservice.repository;

import com.example.stock_microservice.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

    @Query(value = "SELECT * FROM stock WHERE code = :code", nativeQuery = true)
    Optional<StockEntity> findByCode(@Param("code") String code);
}
