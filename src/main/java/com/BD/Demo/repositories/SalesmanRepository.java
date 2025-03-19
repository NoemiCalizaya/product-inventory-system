package com.BD.Demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BD.Demo.entities.Salesman;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman, String>{
    Optional<Salesman> findByFullName(String fullName);
}