package com.microservices.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;
    
    @Column(name = "salesman_ci")
    private String salesmanCi;
    
    @Column(name = "sale_date")
    private LocalDate saleDate;
    
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @Column(name = "state")
    private String state;
    
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleDetail> saleDetails;
} 