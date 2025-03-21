package com.microservices.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesmanDTO {
    private String ci;
    private String fullname;
    private LocalDate birthday;
    private String phoneNumber;
    private String state;
} 