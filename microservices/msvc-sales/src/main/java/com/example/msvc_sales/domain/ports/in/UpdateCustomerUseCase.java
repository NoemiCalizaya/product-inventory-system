package com.example.msvc_sales.domain.ports.in;

import com.example.msvc_sales.domain.models.Customer;

import java.util.Optional;

public interface UpdateCustomerUseCase {
    Customer updateCustomer(String ciCustomer, Customer customer);
}
