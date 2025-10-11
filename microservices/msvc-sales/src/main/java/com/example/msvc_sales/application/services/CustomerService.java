package com.example.msvc_sales.application.services;

import com.example.msvc_sales.application.usecases.CreateCustomerUseCaseImpl;
import com.example.msvc_sales.domain.exception.CustomerNotFoundException;
import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.domain.ports.in.*;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor
public class CustomerService implements CreateCustomerUseCase, RetrieveCustomerUseCase, UpdateCustomerUseCase, DeleteCustomerUseCase {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final RetrieveCustomerUseCase retrieveCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerService(
            CreateCustomerUseCase createCustomerUseCase,
            RetrieveCustomerUseCase retrieveCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.retrieveCustomerUseCase = retrieveCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return createCustomerUseCase.createCustomer(customer);
    }

    @Override
    public Customer activateCustomer(String ciCustomer) {
        return null;
    }

    @Override
    public Customer deactivateCustomer(String ciCustomer) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String ciCustomer) {
        return deleteCustomerUseCase.deleteCustomer(ciCustomer);
    }

    @Override
    public Customer getCustomerById(String ciCustomer) {
        return retrieveCustomerUseCase.getCustomerById(ciCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return retrieveCustomerUseCase.getAllCustomers();
    }

    @Override
    public Customer updateCustomer(String ciCustomer, Customer customer) {
        return updateCustomerUseCase.updateCustomer(ciCustomer, customer);
    }

    /*private final CustomerRepositoryPort customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        customer.validateForCreation();
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String ciCustomer, Customer customer) {
        Customer existingCustomer = getCustomerById(ciCustomer);
        customer.setCiCustomer(ciCustomer);
        customer.setCreatedAt(existingCustomer.getCreatedAt());
        customer.validateForCreation();
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(String ciCustomer) {
        return customerRepository.findById(ciCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + ciCustomer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(String ciCustomer) {
        if (!customerRepository.existsById(ciCustomer)) {
            throw new CustomerNotFoundException("Customer not found with id: " + ciCustomer);
        }
        customerRepository.deleteById(ciCustomer);
    }

    @Override
    public Customer activateCustomer(String ciCustomer) {
        Customer customer = getCustomerById(ciCustomer);
        customer.activate();
        return customerRepository.save(customer);
    }

    @Override
    public Customer deactivateCustomer(String ciCustomer) {
        Customer customer = getCustomerById(ciCustomer);
        customer.deactivate();
        return customerRepository.save(customer);
    }*/
}
