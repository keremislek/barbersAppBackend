package com.example.barbersApp.response;

import com.example.barbersApp.entities.AppointmentStatus;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BarberSummaryResponse {
    private Long barberId; 
    private Long userId;
    private String services;
    private AppointmentStatus status;
    private LocalDate date;
    private String time;
    private Long appId;

}
