package com.example.barbersApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.District;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long>{
    
}
