package com.example.barbersApp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.service.AppointmentsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public AvaliableAppointmentHours getAvaliableAppointment(@PathVariable Long id){
        return appointmentsService.getAvaliableAppointment(id);
    }
    
}
