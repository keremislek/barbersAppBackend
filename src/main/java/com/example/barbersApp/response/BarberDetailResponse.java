package com.example.barbersApp.response;

import java.util.List;

import com.example.barbersApp.entities.Services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberDetailResponse {
    private Long id;
    private String address;
    private String barberName;
    private String photoUrl;
    private List<Services> services;
    private Double rate;
    private AvaliableAppointmentHours avaliableAppointmentHours;
    private int commentSize;


}
