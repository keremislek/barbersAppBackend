package com.example.barbersApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.sql.ast.tree.expression.Summarization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.request.SummaryRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberSummaryResponse;
import com.example.barbersApp.response.CustomerSummaryResponse;
import com.example.barbersApp.response.SummaryResponse;
import com.example.barbersApp.service.AppointmentsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/appointments")
@CrossOrigin
public class AppointmentController {

    private AppointmentsService appointmentsService;

    public AppointmentController(AppointmentsService appointmentsService){
        this.appointmentsService=appointmentsService;
    }
    

    @GetMapping("/getById/{id}")
    public Appointments getAppointmentsById(@PathVariable Long id) {
        return appointmentsService.getAppointmentsById(id);
    }

    @PostMapping("/createOrUpdate")
    public AppointmentsResponse createOrUpdateAppointments(@RequestBody AppointmentsRequest request){
        return appointmentsService.createOrUpdateAppointments(request);
    }

    @GetMapping("/available/{id}")
    public AvaliableAppointmentHours getAvaliableAppointment(@PathVariable Long id, @RequestParam(name="date") LocalDate date){
        return appointmentsService.getAvaliableAppointment(id,date);
    }

    @GetMapping("/{id}")
    public List<BarberDetailResponse> getAvaibleByDistrict(@PathVariable Long id){
        return appointmentsService.getAvaliableByDistrict(id);
    }

    @GetMapping("/date")
    public List<BarberDetailResponse> getAvailableByDate(@RequestParam(name = "date") LocalDate date){
        return appointmentsService.getAvailableByDate(date);
    }

    @GetMapping("/userSummary/{userId}")
    public List<CustomerSummaryResponse> getUserAppointmentSummary(@PathVariable Long userId, @RequestParam(name="date") LocalDate date){
        return appointmentsService.getAppointmentSummary(userId,date);
    }

    @GetMapping("/barberSummary/{barberId}")
    public List<BarberSummaryResponse> getBarberAppointmentSummary(@PathVariable Long barberId, @RequestParam(name="date") LocalDate date){
        return appointmentsService.getBarberAppointmentSummary(barberId,date);
    }

    

    @PutMapping("updateStatus/{appointmentId}")
      public ResponseEntity<String> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam(name = "time") String time,
            @RequestParam(name = "status") String status) {
        boolean updated = appointmentsService.updateAppointmentStatus(appointmentId, time, status);
        if (updated) {
            return ResponseEntity.ok("Appointment status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update appointment status");
        }
    }

    
    
    
}
