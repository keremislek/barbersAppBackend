package com.example.barbersApp.response;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicesInfoResponse {
    private double price;
    private int time;
    private String barberName;
    private String  servicesName;
}
