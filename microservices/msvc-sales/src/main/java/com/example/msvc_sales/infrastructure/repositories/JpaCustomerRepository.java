package com.example.msvc_sales.infrastructure.repositories;

import com.example.msvc_sales.infrastructure.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, String> {

}
