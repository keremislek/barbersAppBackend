package com.example.barbersApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long>{


}
