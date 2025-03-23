package com.microservices.purchase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;
    
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @Column(name = "salesman_ci")
    private String salesmanCi;
    
    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;
    
    @Column(name = "purchase_cost")
    private BigDecimal purchaseCost;
    
    @Column(name = "state")
    private String state;
    
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Batch> batches;
} 