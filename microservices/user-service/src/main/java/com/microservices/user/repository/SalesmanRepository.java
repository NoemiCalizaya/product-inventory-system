package com.microservices.user.repository;

import com.microservices.user.entity.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman, String> {
    List<Salesman> findByState(String state);
    Optional<Salesman> findByEmail(String email);
    boolean existsByEmail(String email);
} 