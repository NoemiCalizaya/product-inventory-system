package com.BD.Demo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salesmen")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Salesman {
    @Id
    private String ci;
    @Column(name = "full_name", length = 100)
    private String fullName;
    private LocalDate birthday;
    @Column(name = "phone_number" ,length = 20)
    private String phoneNumber;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean state;
}
