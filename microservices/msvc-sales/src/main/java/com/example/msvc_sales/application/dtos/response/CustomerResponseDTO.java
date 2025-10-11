package com.example.msvc_sales.application.dtos.response;

import com.example.msvc_sales.domain.valueobject.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String ciCustomer;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate birthday;
    private Integer age;
    private String email;
    private String phone;
    private String address;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Incluye campos calculados y de auditoría para respuesta
}
