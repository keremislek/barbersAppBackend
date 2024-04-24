package com.example.barbersApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Barber;


@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
	Optional<Barber> findByEmail(String email);
}
