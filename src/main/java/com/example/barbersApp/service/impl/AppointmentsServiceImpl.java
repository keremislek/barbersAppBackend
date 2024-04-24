package com.example.barbersApp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.repository.AppointmentRepository;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.service.AppointmentsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {
    

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointments getAppointmentsById(Long id) {
        Appointments appointments=appointmentRepository.findById(id).orElse(null);
        return appointments;
    }

    @Override
    public AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request) {
        AppointmentsResponse appointmentsResponse = new AppointmentsResponse();
        List<Appointments> appointmentsList = new ArrayList<>();
        Appointments appointments = new Appointments();
        for(Long serviceId:request.getServiceIdList()){
            appointments= appointmentRepository.findAppointmentsWithParams(request.getUserId(), request.getBarberId(), serviceId, request.getDate());
            if(appointments!=null){
                updateAppointmentsIfNeeded(request, appointments);
                appointmentRepository.save(appointments);
                
            } else {
                appointments = convertToAppointments(request,serviceId);
                appointmentRepository.save(appointments);
            }

            appointmentsList.add(appointments);
            
        }
        appointmentsResponse.setAppointments(appointmentsList);

        return appointmentsResponse;
    }


    private void updateAppointmentsIfNeeded(AppointmentsRequest request, Appointments appointments) {
        if (!"T".equals(appointments.getT1())) {
            appointments.setT1(request.getT1());
        }
        if (!"T".equals(appointments.getT2())) {
            appointments.setT2(request.getT2());
        }
        if (!"T".equals(appointments.getT3())) {
            appointments.setT3(request.getT3());
        }
        if (!"T".equals(appointments.getT4())) {
            appointments.setT4(request.getT4());
        }
        if (!"T".equals(appointments.getT5())) {
            appointments.setT5(request.getT5());
        }
        if (!"T".equals(appointments.getT6())) {
            appointments.setT6(request.getT6());
        }
        if (!"T".equals(appointments.getT7())) {
            appointments.setT7(request.getT7());
        }
        if (!"T".equals(appointments.getT8())) {
            appointments.setT8(request.getT8());
        }
        if (!"T".equals(appointments.getT9())) {
            appointments.setT9(request.getT9());
        }
        if (!"T".equals(appointments.getT10())) {
            appointments.setT10(request.getT10());
        }
        if (!"T".equals(appointments.getT11())) {
            appointments.setT11(request.getT11());
        }
        if (!"T".equals(appointments.getT12())) {
            appointments.setT12(request.getT12());
        }
    }


    public static Appointments convertToAppointments(AppointmentsRequest request, Long serviceId) {
        Appointments appointments = new Appointments();
        appointments.setUserId(request.getUserId());
        appointments.setBarberId(request.getBarberId());
        appointments.setServiceId(serviceId);
        appointments.setDate(request.getDate());
        appointments.setT1(request.getT1());
        appointments.setT2(request.getT2());
        appointments.setT3(request.getT3());
        appointments.setT4(request.getT4());
        appointments.setT5(request.getT5());
        appointments.setT6(request.getT6());
        appointments.setT7(request.getT7());
        appointments.setT8(request.getT8());
        appointments.setT9(request.getT9());
        appointments.setT10(request.getT10());
        appointments.setT11(request.getT11());
        appointments.setT12(request.getT12());
        return appointments;
    }

    @Override
    public AvaliableAppointmentHours getAvaliableAppointment(Long id) {
        Appointments appointments=appointmentRepository.findByBarberId(id);
        
        if(appointments==null){
            return AvaliableAppointmentHours.builder()
            .t1("false")
            .t2("false")
            .t3("false")
            .t4("false")
            .t5("false")
            .t6("false")
            .t7("false")
            .t8("false")
            .t9("false")
            .t10("false")
            .t11("false")
            .t12("false")
            .build();
        }else{
            return AvaliableAppointmentHours.builder()
            .t1(appointments.getT1())
            .t2(appointments.getT2())
            .t3(appointments.getT3())
            .t4(appointments.getT4())
            .t5(appointments.getT5())
            .t6(appointments.getT6())
            .t7(appointments.getT7())
            .t8(appointments.getT8())
            .t9(appointments.getT9())
            .t10(appointments.getT10())
            .t11(appointments.getT11())
            .t12(appointments.getT12())
            .build();
        }
        
    }

}
