package com.example.msvc_sales.application.mapper;

import com.example.msvc_sales.application.dtos.request.CustomerRequestDTO;
import com.example.msvc_sales.application.dtos.response.CustomerResponseDTO;
import com.example.msvc_sales.domain.models.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerRequestDTO dto) {
        if (dto == null) return null;

        return Customer.builder()
                .ciCustomer(dto.getCiCustomer())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthday(dto.getBirthday())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(dto.getStatus())
                .build();
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        if (customer == null) return null;

        return new CustomerResponseDTO(
                customer.getCiCustomer(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getFullName(),
                customer.getBirthday(),
                customer.getAge(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public void updateDomainFromDTO(CustomerRequestDTO dto, Customer customer) {
        if (dto == null || customer == null) return;

        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getBirthday() != null) customer.setBirthday(dto.getBirthday());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());
        if (dto.getStatus() != null) customer.setStatus(dto.getStatus());
    }

    public List<CustomerResponseDTO> toResponseDTOList(List<Customer> customers) {
        if (customers == null) return null;
        return customers.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
