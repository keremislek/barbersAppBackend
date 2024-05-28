package com.example.barbersApp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.AppointmentSummary;
import com.example.barbersApp.response.SummaryResponse;


@Repository
public interface AppointmentSummaryRepository extends JpaRepository<AppointmentSummary, Long>{

    List<SummaryResponse> findByAppointmentIdAndUserId(Long appointmentId, Long userId);

    List<AppointmentSummary> findByAppointmentId(Long long1);

    List<AppointmentSummary> findByUserIdAndDate(Long userId, LocalDate date);

}
