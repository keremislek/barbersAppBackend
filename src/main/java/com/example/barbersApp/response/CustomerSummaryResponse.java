package com.example.barbersApp.response;

import com.example.barbersApp.entities.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSummaryResponse {

    private Long barberId; 
    private Long userId;
    private String services;
    private AppointmentStatus status;
    private LocalDate date;
    private String time;
    private Long appId;
    private String barberName;
    private String customerName;


}
