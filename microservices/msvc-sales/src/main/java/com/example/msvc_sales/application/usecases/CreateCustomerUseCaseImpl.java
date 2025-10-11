package com.example.msvc_sales.application.usecases;

import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.domain.ports.in.CreateCustomerUseCase;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CreateCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Customer activateCustomer(String ciCustomer) {
        return null;
    }

    @Override
    public Customer deactivateCustomer(String ciCustomer) {
        return null;
    }
}
