package com.example.barbersApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.AdressesInfo;



@Repository
public interface AddressesInfoRepository extends JpaRepository<AdressesInfo,Long> {
    List<AdressesInfo> findByBarberId(Long id);

    List<AdressesInfo> findByDistrictId(Long id);
}
