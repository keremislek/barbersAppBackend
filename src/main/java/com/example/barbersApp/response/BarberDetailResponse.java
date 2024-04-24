package com.example.barbersApp.response;

import java.util.List;

import com.example.barbersApp.entities.Services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarberDetailResponse {
    private Long id;
    private String address;
    private String barberName;
    private String photoUrl;
    private List<Services> services;
}
