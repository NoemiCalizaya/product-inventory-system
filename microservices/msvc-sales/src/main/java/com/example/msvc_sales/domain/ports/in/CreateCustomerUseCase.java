package com.example.msvc_sales.domain.ports.in;

import com.example.msvc_sales.domain.models.Customer;

public interface CreateCustomerUseCase {
    Customer createCustomer(Customer customer);

    Customer activateCustomer(String ciCustomer);
    Customer deactivateCustomer(String ciCustomer);
}
