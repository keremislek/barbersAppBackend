package com.example.barbersApp.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.ServicesInfo;

@Repository
public interface ServicesInfoRepository extends JpaRepository<ServicesInfo,Long>{
    List<ServicesInfo> findByBarberId(Long id); 
}
