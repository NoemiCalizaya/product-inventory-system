package com.example.msvc_purchases.entity;

import com.example.msvc_purchases.utils.SupplierStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.processing.Pattern;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(nullable = false, unique = true, length = 50)
    private String supplierCode;

    @Column(nullable = false, length = 150)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SupplierStatus status = SupplierStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relación con órdenes de compra
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrder> purchaseOrders;
}
