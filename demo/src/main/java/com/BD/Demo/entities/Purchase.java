package com.BD.Demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_ci", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore 
    private List<Batch> batches;

    @Column(name = "date_acquisition", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate  dateAcquisition;
    @Column(name = "purchase_cost", precision = 10, scale = 2)
    private BigDecimal purchaseCost;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
