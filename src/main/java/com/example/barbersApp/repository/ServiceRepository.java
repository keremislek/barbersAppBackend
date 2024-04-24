package com.example.barbersApp.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Services;



@Repository
public interface ServiceRepository extends JpaRepository<Services,Long>{
}
