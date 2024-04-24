package com.example.barbersApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

}
