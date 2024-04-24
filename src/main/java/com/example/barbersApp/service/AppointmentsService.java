package com.example.barbersApp.service;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;




public interface AppointmentsService {

    Appointments getAppointmentsById(Long id);
    AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request);
    AvaliableAppointmentHours getAvaliableAppointment(Long id);

}
