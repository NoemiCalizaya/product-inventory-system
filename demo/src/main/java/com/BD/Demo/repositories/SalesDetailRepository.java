package com.BD.Demo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BD.Demo.dto.response.ProductResponseDTO;
import com.BD.Demo.dto.response.SaleResponseDTO;
import com.BD.Demo.entities.SalesDetail;

@Repository
public interface SalesDetailRepository extends JpaRepository<SalesDetail, Long>{
    @Query("SELECT new com.BD.Demo.dto.response.ProductResponseDTO(d.batch.product.productName, SUM(d.quantity), d.batch.product.salePrice) " +
        "FROM SalesDetail d " +
        "GROUP BY d.batch.product.id, d.batch.product.productName, d.batch.product.salePrice " +
        "ORDER BY SUM(d.quantity) DESC")
    List<ProductResponseDTO> findTopSellingProducts(Pageable pageable);

    @Query("SELECT d FROM SalesDetail d WHERE d.sale.saleDate BETWEEN :startDate AND :endDate")
    List<SalesDetail> findSalesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
