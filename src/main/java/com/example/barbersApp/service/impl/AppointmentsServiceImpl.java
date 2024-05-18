package com.example.barbersApp.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.entities.AppointmentSummary;
import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Comment;
import com.example.barbersApp.entities.Rating;
import com.example.barbersApp.repository.AddressesInfoRepository;
import com.example.barbersApp.repository.AppointmentRepository;
import com.example.barbersApp.repository.AppointmentSummaryRepository;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CommentRepository;
import com.example.barbersApp.repository.RatingRepository;
import com.example.barbersApp.request.AppointmentsRequest;
import com.example.barbersApp.request.SummaryRequest;
import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.SummaryResponse;
import com.example.barbersApp.service.AppointmentsService;

import jakarta.persistence.EntityNotFoundException;


import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import scala.annotation.meta.field;

@Service
@RequiredArgsConstructor
public class AppointmentsServiceImpl implements AppointmentsService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final AddressesInfoRepository addressesInfoRepository;
    private final AppointmentSummaryRepository appointmentSummaryRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    @Override
    public Appointments getAppointmentsById(Long id) {
        Appointments appointments = appointmentRepository.findById(id).orElse(null);
        return appointments;
    }

    @Override
    public AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request) {
        AppointmentsResponse appointmentsResponse = new AppointmentsResponse();
        Appointments appointments = new Appointments();

        appointments = appointmentRepository.findAppointmentsWithParams(request.getBarberId(),request.getDate());
        if (appointments != null) {
            updateAppointmentsIfNeeded(request, appointments);
            appointmentRepository.save(appointments);

        } else {
            appointments = convertToAppointments(request);
            appointmentRepository.save(appointments);
        }
        

        for (Long serviceId : request.getServiceIds()){
            AppointmentSummary appointmentSummary = new AppointmentSummary();
            appointmentSummary.setAppointmentId(appointments.getId());
            appointmentSummary.setServiceId(serviceId);
            appointmentSummary.setUserId(request.getUserId());

            appointmentSummaryRepository.save(appointmentSummary);
        }



        appointmentsResponse.setAppointments(appointments);
   

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

    public static Appointments convertToAppointments(AppointmentsRequest request) {
        Appointments appointments = new Appointments();
        appointments.setBarberId(request.getBarberId());
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
        Appointments appointments = appointmentRepository.findByBarberId(id);

        if (appointments == null) {
            return AvaliableAppointmentHours.builder()
                    .t1("F")
                    .t2("F")
                    .t3("F")
                    .t4("F")
                    .t5("F")
                    .t6("F")
                    .t7("F")
                    .t8("F")
                    .t9("F")
                    .t10("F")
                    .t11("F")
                    .t12("F")
                    .build();
        } else {
            AvaliableAppointmentHours avaliableAppointmentHours = new AvaliableAppointmentHours();
            avaliableAppointmentHours.setT1(appointments.getT1());
            avaliableAppointmentHours.setT2(appointments.getT2());
            avaliableAppointmentHours.setT3(appointments.getT3());
            avaliableAppointmentHours.setT4(appointments.getT4());
            avaliableAppointmentHours.setT5(appointments.getT5());
            avaliableAppointmentHours.setT6(appointments.getT6());
            avaliableAppointmentHours.setT7(appointments.getT7());
            avaliableAppointmentHours.setT8(appointments.getT8());
            avaliableAppointmentHours.setT9(appointments.getT9());
            avaliableAppointmentHours.setT10(appointments.getT10());
            avaliableAppointmentHours.setT11(appointments.getT11());
            avaliableAppointmentHours.setT12(appointments.getT12());

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
        List<Rating> ratings=ratingRepository.findByBarberId(Optional.of(barber.getId()));
        List<Comment> comments = commentRepository.findByBarberId(barber.getId());

        double avg = 0;
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                avg += rating.getRate();
            }
            avg /= ratings.size();
        }
        
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
                .rate(avg)
                .commentSize(comments.size())
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

    @Override
    public List<SummaryResponse> getAppointmentSummary(SummaryRequest request) {
        List<SummaryResponse> responses = appointmentSummaryRepository.findByAppointmentIdAndUserId(request.getAppointmentId(), request.getUserId());
        return responses;
    }

    @Override
    public AvaliableAppointmentHours getAvaliableAppointmentByDate(Long id) {
        Appointments appointments = new Appointments();
        AvaliableAppointmentHours avaliableAppointmentHours = new AvaliableAppointmentHours();
        appointments = appointmentRepository.findAppointmentsWithParams(id, LocalDate.now());

        if (appointments == null) {
            return AvaliableAppointmentHours.builder()
                    .t1("F")
                    .t2("F")
                    .t3("F")
                    .t4("F")
                    .t5("F")
                    .t6("F")
                    .t7("F")
                    .t8("F")
                    .t9("F")
                    .t10("F")
                    .t11("F")
                    .t12("F")
                    .build();
        } else {
            
            avaliableAppointmentHours.setT1(appointments.getT1());
            avaliableAppointmentHours.setT2(appointments.getT2());
            avaliableAppointmentHours.setT3(appointments.getT3());
            avaliableAppointmentHours.setT4(appointments.getT4());
            avaliableAppointmentHours.setT5(appointments.getT5());
            avaliableAppointmentHours.setT6(appointments.getT6());
            avaliableAppointmentHours.setT7(appointments.getT7());
            avaliableAppointmentHours.setT8(appointments.getT8());
            avaliableAppointmentHours.setT9(appointments.getT9());
            avaliableAppointmentHours.setT10(appointments.getT10());
            avaliableAppointmentHours.setT11(appointments.getT11());
            avaliableAppointmentHours.setT12(appointments.getT12());
        }
        return avaliableAppointmentHours;
    }

}
