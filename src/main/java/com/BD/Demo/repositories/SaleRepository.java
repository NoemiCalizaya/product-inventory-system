package com.BD.Demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BD.Demo.dto.response.SalesDTO;
import com.BD.Demo.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.BD.Demo.dto.response.SalesDTO(s.saleId, sm.fullName, s.saleDate, s.totalAmount, s.state) " +
        "FROM Sale s JOIN s.salesman sm")
    List<SalesDTO> findAllSalesWithSalesmanName();

}
