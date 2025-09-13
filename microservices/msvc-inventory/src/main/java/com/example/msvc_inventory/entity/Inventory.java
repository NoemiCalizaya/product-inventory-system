package com.example.msvc_inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable = 0;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered = 0;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

}
