package com.example.barbersApp.entities;

import java.time.LocalDate;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointment_summary")
public class AppointmentSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long appointmentId;
    Long serviceId;
    Long userId;
    @Convert(converter = AppointmentStatusConverter.class)
    private AppointmentStatus status;
    LocalDate date;
    String time;
    
}
