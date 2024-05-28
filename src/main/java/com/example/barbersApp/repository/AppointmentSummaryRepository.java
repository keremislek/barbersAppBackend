package com.example.barbersApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.AppointmentSummary;
import com.example.barbersApp.response.SummaryResponse;

import scala.Long;

@Repository
public interface AppointmentSummaryRepository extends JpaRepository<AppointmentSummary, Long>{

    List<SummaryResponse> findByAppointmentIdAndUserId(Long appointmentId, Long userId);

}
