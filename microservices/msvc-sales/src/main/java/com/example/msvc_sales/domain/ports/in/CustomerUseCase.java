package com.example.msvc_sales.domain.ports.in;

import com.example.msvc_sales.domain.models.Customer;

import java.util.List;

public interface CustomerUseCase {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(String ciCustomer, Customer customer);
    Customer getCustomerById(String ciCustomer);
    List<Customer> getAllCustomers();
    void deleteCustomer(String ciCustomer);
    Customer activateCustomer(String ciCustomer);
    Customer deactivateCustomer(String ciCustomer);
}