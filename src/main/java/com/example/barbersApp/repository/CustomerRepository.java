package com.example.barbersApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Optional<Customer> findByEmail(String email);
	Customer getById(Long id);
}
