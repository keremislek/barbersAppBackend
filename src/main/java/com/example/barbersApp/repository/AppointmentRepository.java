package com.example.barbersApp.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.barbersApp.entities.Appointments;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    @Query("SELECT a FROM Appointments a WHERE a.userId = :userId AND a.barberId = :barberId AND a.serviceId = :serviceId AND a.date = :date")
    Appointments findAppointmentsWithParams(@Param("userId") Long userId, @Param("barberId") Long barberId, @Param("serviceId") Long serviceId, @Param("date") LocalDate date);

    Appointments findByBarberId(Long id);
}
