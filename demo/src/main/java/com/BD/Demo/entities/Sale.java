package com.BD.Demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salesman_ci", nullable = false)
    private Salesman salesman;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<SalesDetail> salesDetail;

    @Column(name = "sale_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate saleDate;
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean state;
}
