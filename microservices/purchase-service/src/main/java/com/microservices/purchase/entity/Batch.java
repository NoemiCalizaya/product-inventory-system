package com.microservices.purchase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long batchId;
    
    @Column(name = "product_cod")
    private String productCod;
    
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    
    @Column(name = "batch_number")
    private String batchNumber;
    
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "cost_unit")
    private BigDecimal costUnit;
    
    @Column(name = "state")
    private String state;
} 