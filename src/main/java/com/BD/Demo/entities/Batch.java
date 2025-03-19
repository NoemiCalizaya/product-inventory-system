package com.BD.Demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "batches")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long batchId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_cod", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore 
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @OneToMany(mappedBy = "batch", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SalesDetail> salesDetail;

    @Column(name = "batch_number", length = 50, nullable = false)
    private String batchNumber;
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    @Column(name = "manufacturing_date", nullable = true)
    private LocalDate manufacturingDate;
    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer quantity;
    @Column(name = "cost_unit", precision = 10, scale = 2)
    private BigDecimal costUnit;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
