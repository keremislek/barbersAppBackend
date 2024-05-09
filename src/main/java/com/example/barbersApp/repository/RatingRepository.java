package com.example.barbersApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Rating;
import com.example.barbersApp.response.BarberTop5Query;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Long>{

    List<Rating> findByCustomerIdAndBarberId(Optional<Long> customerId, Optional<Long> barberId);

    List<Rating> findByBarberId(Optional<Long> barberId);

    List<Rating> findByCustomerId(Optional<Long> customerId);

    Integer findByBarberId(Long id);

    @Query("SELECT barber.id, AVG(rate) AS avgrate FROM Rating r GROUP BY r.barber.id ORDER BY avgrate DESC")
    List<Object []> findfamousByRating(Pageable pageable);


}
