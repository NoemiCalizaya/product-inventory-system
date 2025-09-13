package com.example.msvc_products.repository;

import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByProduct_ProductCode(String productCode);

    Optional<Batch> findByPurchaseOrderIdAndProductCode(Long purchaseOrderId, String productCode);
}
