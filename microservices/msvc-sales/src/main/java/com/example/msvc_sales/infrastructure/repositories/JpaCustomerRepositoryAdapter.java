package com.example.msvc_sales.infrastructure.repositories;

import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;
import com.example.msvc_sales.infrastructure.entities.CustomerEntity;
import com.example.msvc_sales.infrastructure.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerEntityMapper entityMapper;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = entityMapper.toEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(String ciCustomer) {
        return jpaCustomerRepository.findById(ciCustomer)
                .map(entityMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        return jpaCustomerRepository.findById(customer.getCiCustomer())
                .map(existingEntity -> {
                    entityMapper.updateEntityFromDomain(customer, existingEntity);
                    CustomerEntity savedEntity = jpaCustomerRepository.saveAndFlush(existingEntity);
                    return entityMapper.toDomain(savedEntity);
                });
    }

    @Override
    public boolean deleteById(String ciCustomer) {
        if(jpaCustomerRepository.existsById(ciCustomer)) {
            jpaCustomerRepository.deleteById(ciCustomer);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsById(String ciCustomer) {
        return jpaCustomerRepository.existsById(ciCustomer);
    }
}
