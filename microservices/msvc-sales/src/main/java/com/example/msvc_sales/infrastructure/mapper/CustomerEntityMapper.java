package com.example.msvc_sales.infrastructure.mapper;

import com.example.msvc_sales.domain.models.Customer;
import com.example.msvc_sales.infrastructure.entities.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerEntityMapper {

    // Domain -> Entity
    public CustomerEntity toEntity(Customer customer) {
        if (customer == null) return null;

        CustomerEntity entity = new CustomerEntity();
        entity.setCiCustomer(customer.getCiCustomer());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setBirthday(customer.getBirthday());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());
        entity.setStatus(customer.getStatus());

        return entity;
    }

    // Entity -> Domain
    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;

        return Customer.builder()
                .ciCustomer(entity.getCiCustomer())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthday(entity.getBirthday())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    // Actualizar entity desde domain
    public void updateEntityFromDomain(Customer customer, CustomerEntity entity) {
        if (customer == null || entity == null) return;

        // Ignorando ciCustomer, createdAt, updatedAt
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setBirthday(customer.getBirthday());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setAddress(customer.getAddress());
        entity.setStatus(customer.getStatus());
    }

    // Lista de entities -> lista de domain
    public List<Customer> toDomainList(List<CustomerEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // Lista de domain -> lista de entities
    public List<CustomerEntity> toEntityList(List<Customer> customers) {
        return customers.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
