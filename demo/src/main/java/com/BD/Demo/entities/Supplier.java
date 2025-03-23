package com.BD.Demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "supplier_name", length = 100, nullable = false)
    private String supplierName;
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;
    @Column(length = 200, nullable = false)
    private String address;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
