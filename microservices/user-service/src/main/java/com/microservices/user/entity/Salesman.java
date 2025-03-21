package com.microservices.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesmen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salesman {
    @Id
    @Column(nullable = false)
    private String ci;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String state = "ACTIVE"; // Estado por defecto
} 