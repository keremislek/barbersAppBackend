package com.example.barbersApp.service;

import java.util.List;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;




public interface AppointmentsService {

    Appointments getAppointmentsById(Long id);
    AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request);
    AvaliableAppointmentHours getAvaliableAppointment(Long id);
    List<BarberDetailResponse> getAvaliableByDistrict(Long id);

}
