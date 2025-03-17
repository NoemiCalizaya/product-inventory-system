package com.BD.Demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchases")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_ci", nullable = false)
    private Supplier supplier;

    @Column(name = "date_acquisition", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate  dateAcquisition;
    @Column(name = "purchase_cost", precision = 10, scale = 2)
    private BigDecimal purchaseCost;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
