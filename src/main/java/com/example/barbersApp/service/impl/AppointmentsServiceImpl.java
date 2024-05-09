package com.example.barbersApp.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.repository.AddressesInfoRepository;
import com.example.barbersApp.repository.AppointmentRepository;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.service.AppointmentsService;

import jakarta.persistence.EntityNotFoundException;


import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final AddressesInfoRepository addressesInfoRepository;

    @Override
    public Appointments getAppointmentsById(Long id) {
        Appointments appointments = appointmentRepository.findById(id).orElse(null);
        return appointments;
    }

    @Override
    public AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request) {
        AppointmentsResponse appointmentsResponse = new AppointmentsResponse();
        List<Appointments> appointmentsList = new ArrayList<>();
        Appointments appointments = new Appointments();
        for (Long serviceId : request.getServiceIdList()) {
            appointments = appointmentRepository.findAppointmentsWithParams(request.getUserId(), request.getBarberId(),
                    serviceId, request.getDate());
            if (appointments != null) {
                updateAppointmentsIfNeeded(request, appointments);
                appointmentRepository.save(appointments);

            } else {
                appointments = convertToAppointments(request, serviceId);
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
        List<Appointments> appointments = appointmentRepository.findByBarberId(id);

        if (appointments == null || appointments.isEmpty()) {
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
        } else {
            AvaliableAppointmentHours avaliableAppointmentHours = new AvaliableAppointmentHours();
            int size=appointments.size();
            Appointments firstAppointments = appointments.get(size-1);
            avaliableAppointmentHours.setT1(firstAppointments.getT1());
            avaliableAppointmentHours.setT2(firstAppointments.getT2());
            avaliableAppointmentHours.setT3(firstAppointments.getT3());
            avaliableAppointmentHours.setT4(firstAppointments.getT4());
            avaliableAppointmentHours.setT5(firstAppointments.getT5());
            avaliableAppointmentHours.setT6(firstAppointments.getT6());
            avaliableAppointmentHours.setT7(firstAppointments.getT7());
            avaliableAppointmentHours.setT8(firstAppointments.getT8());
            avaliableAppointmentHours.setT9(firstAppointments.getT9());
            avaliableAppointmentHours.setT10(firstAppointments.getT10());
            avaliableAppointmentHours.setT11(firstAppointments.getT11());
            avaliableAppointmentHours.setT12(firstAppointments.getT12());

            return avaliableAppointmentHours;
        }

    }

    @Override
    public List<BarberDetailResponse> getAvaliableByDistrict(Long id) {
        List<AdressesInfo> addressesInfos = addressesInfoRepository.findByDistrictId(id);

        List<Long> barbersId = new ArrayList<>();
        for (AdressesInfo adressesInfo : addressesInfos) {
            barbersId.add(adressesInfo.getBarber().getId());
        }
        List<Barber> barber = new ArrayList<>();
        for (Long barberId : barbersId) {
            barber.add(barberRepository.findById(barberId)
                    .orElseThrow(() -> new EntityNotFoundException("barber not found: " + barberId)));
        }

        return barber.stream().map(this::mapToBarberResponse).collect(Collectors.toList());

    }

    private BarberDetailResponse mapToBarberResponse(Barber barber) {
        String fullAddress = null;
        if (barber.getAddressesInfo() != null) {
            fullAddress = barber.getAddressesInfo().getFullAddress();
        }
        if (fullAddress == null) {
            fullAddress = "Bilinmeyen Adres";
        }
        return BarberDetailResponse.builder().barberName(barber.getBarberName())
                .id(barber.getId())
                .photoUrl(barber.getPhotoUrl())
                .address(fullAddress)
                .build();
    }

    // bütün berberlerden meşgul olanları çıkar
    @Override
    public List<BarberDetailResponse> getAvailableByDate(LocalDate date) {
        List<Appointments> appointments = appointmentRepository.findByDate(date);// boşsa bütün berberler müsait

        List<Barber> allBarbers=barberRepository.findAll();
        List<Long> barbersId = new ArrayList<>();

        for (Barber barber : allBarbers) {
            barbersId.add(barber.getId());
        }


        if (appointments == null || appointments.isEmpty()) {
            List<Barber> barbers = barberRepository.findAll();
            return barbers.stream().map(this::mapToBarberResponse).collect(Collectors.toList());
        }

        
        List<Long> barbersToRemove = new ArrayList<>();
        for (Long barberId : barbersId) {
            AvaliableAppointmentHours availableAppointmentHours = getAvaliableAppointment(barberId);
            List<String> timeList = new ArrayList<>();
            timeList.add(availableAppointmentHours.getT1());
            timeList.add(availableAppointmentHours.getT2());
            timeList.add(availableAppointmentHours.getT3());
            timeList.add(availableAppointmentHours.getT4());
            timeList.add(availableAppointmentHours.getT5());
            timeList.add(availableAppointmentHours.getT6());
            timeList.add(availableAppointmentHours.getT7());
            timeList.add(availableAppointmentHours.getT8());
            timeList.add(availableAppointmentHours.getT9());
            timeList.add(availableAppointmentHours.getT10());
            timeList.add(availableAppointmentHours.getT11());
            timeList.add(availableAppointmentHours.getT12());
        
            int a = 0;
            for (String time : timeList) {
                if (time.equalsIgnoreCase("T")) {
                    a++;
                } else {
                    break;
                }
            }
            
            if (a == 12) {
                barbersToRemove.add(barberId);
            }
        }
        
        barbersId.removeAll(barbersToRemove);

        List<BarberDetailResponse> barbersResponse= new ArrayList<>();
        for (Long barberId : barbersId) {
            Barber barber= barberRepository.findById(barberId).orElseThrow(()-> new EntityNotFoundException("Barbers not found with id : "+barberId));
            if(barber!=null){
                barbersResponse.add(mapToBarberResponse(barber));
            }
        }
        

        return barbersResponse;

    }

}
