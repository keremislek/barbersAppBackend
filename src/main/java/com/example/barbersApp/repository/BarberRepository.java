package com.example.barbersApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.response.FamousBarbers;


@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
	Optional<Barber> findByEmail(String email);

	@Query("SELECT b FROM Barber b WHERE LOWER(b.barberName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Barber> findByNameToDatabase(String name);

	
}
