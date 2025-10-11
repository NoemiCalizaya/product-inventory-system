package com.example.msvc_sales.application.dtos.request;

import com.example.msvc_sales.domain.valueobject.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String ciCustomer;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private String address;
    private CustomerStatus status;
}
