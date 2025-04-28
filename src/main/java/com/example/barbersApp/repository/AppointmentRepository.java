package com.example.barbersApp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Appointments;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    @Query("SELECT a FROM Appointments a WHERE a.barberId = :barberId AND a.date = :date")
    Appointments findAppointmentsWithParams(@Param("barberId") Long barberId,  @Param("date") LocalDate date);

    Appointments findByBarberId(Long id);
    List<Appointments> findByDate(LocalDate date);
}
