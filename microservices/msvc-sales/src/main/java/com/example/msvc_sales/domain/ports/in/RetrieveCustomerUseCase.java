package com.example.msvc_sales.domain.ports.in;

import com.example.msvc_sales.domain.models.Customer;

import java.util.List;
import java.util.Optional;

public interface RetrieveCustomerUseCase {
    Customer getCustomerById(String ciCustomer);
    List<Customer> getAllCustomers();
}
