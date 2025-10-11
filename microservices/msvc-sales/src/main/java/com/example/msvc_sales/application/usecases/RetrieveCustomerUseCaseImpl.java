package com.example.msvc_sales.application.usecases;

import com.example.msvc_sales.domain.exception.CustomerNotFoundException;
import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.domain.ports.in.RetrieveCustomerUseCase;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;

import java.util.List;
import java.util.Optional;

public class RetrieveCustomerUseCaseImpl implements RetrieveCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public RetrieveCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer getCustomerById(String ciCustomer) {
        return customerRepositoryPort.findById(ciCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + ciCustomer));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepositoryPort.findAll();
    }
}
