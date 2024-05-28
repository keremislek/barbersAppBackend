package com.example.barbersApp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.request.SummaryRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberSummaryResponse;
import com.example.barbersApp.response.CustomerSummaryResponse;
import com.example.barbersApp.response.SummaryResponse;




public interface AppointmentsService {

    Appointments getAppointmentsById(Long id);
    AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request);
    AvaliableAppointmentHours getAvaliableAppointment(Long id,LocalDate date);
    AvaliableAppointmentHours getAvaliableAppointmentByDate(Long id);
    List<BarberDetailResponse> getAvaliableByDistrict(Long id);
    List<BarberDetailResponse> getAvailableByDate(LocalDate date);
   
    List<BarberSummaryResponse> getBarberAppointmentSummary(Long barberId, LocalDate date);
    boolean updateAppointmentStatus(Long appointmentId, String time, String status);
    List<CustomerSummaryResponse> getAppointmentSummary(Long userId, LocalDate date);

}
