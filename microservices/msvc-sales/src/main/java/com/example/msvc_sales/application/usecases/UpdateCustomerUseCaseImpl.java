package com.example.msvc_sales.application.usecases;

import com.example.msvc_sales.domain.exception.CustomerNotFoundException;
import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.domain.ports.in.UpdateCustomerUseCase;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;

import java.util.Optional;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public UpdateCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer updateCustomer(String ciCustomer, Customer customer) {
        return customerRepositoryPort.update(customer)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + ciCustomer
                        ));
    }
}
