package com.microservices.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalesmanRequest {
    private String ci;
    private String name;
    private String email;
} 