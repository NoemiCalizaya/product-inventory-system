package com.example.msvc_sales.application.usecases;

import com.example.msvc_sales.domain.exception.CustomerNotFoundException;
import com.example.msvc_sales.domain.ports.in.DeleteCustomerUseCase;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;

public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public DeleteCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public boolean deleteCustomer(String ciCustomer) {
        if (customerRepositoryPort.existsById(ciCustomer))
            return customerRepositoryPort.deleteById(ciCustomer);
        else
            throw new CustomerNotFoundException("Customer not found with id: " + ciCustomer);
    }

}
