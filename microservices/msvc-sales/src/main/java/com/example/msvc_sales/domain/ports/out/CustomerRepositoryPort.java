package com.example.msvc_sales.domain.ports.out;

import com.example.msvc_sales.domain.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(String ciCustomer);
    List<Customer> findAll();
    Optional<Customer> update(Customer customer);
    boolean deleteById(String ciCustomer);
    boolean existsById(String ciCustomer);
}