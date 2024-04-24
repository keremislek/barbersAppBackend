package com.example.barbersApp.response;


import java.util.List;

import com.example.barbersApp.entities.Appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsResponse {
    private List<Appointments> appointments;

}
