package com.example.barbersApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.AdressesInfo;


@Repository
public interface AddressesInfoRepository extends JpaRepository<AdressesInfo,Long> {
    
}
