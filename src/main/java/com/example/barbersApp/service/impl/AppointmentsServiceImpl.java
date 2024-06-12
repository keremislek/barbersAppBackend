package com.example.barbersApp.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.entities.AppointmentStatus;
import com.example.barbersApp.entities.AppointmentSummary;
import com.example.barbersApp.entities.Appointments;
import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Comment;
import com.example.barbersApp.entities.Customer;
import com.example.barbersApp.entities.Rating;
import com.example.barbersApp.entities.Services;
import com.example.barbersApp.entities.ServicesInfo;
import com.example.barbersApp.repository.AddressesInfoRepository;
import com.example.barbersApp.repository.AppointmentRepository;
import com.example.barbersApp.repository.AppointmentSummaryRepository;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CommentRepository;
import com.example.barbersApp.repository.CustomerRepository;
import com.example.barbersApp.repository.RatingRepository;
import com.example.barbersApp.repository.ServiceRepository;
import com.example.barbersApp.repository.ServicesInfoRepository;
import com.example.barbersApp.request.AppointmentsRequest;

import com.example.barbersApp.response.AppointmentsResponse;
import com.example.barbersApp.response.AvaliableAppointmentHours;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberSummaryResponse;
import com.example.barbersApp.response.CustomerSummaryResponse;
import com.example.barbersApp.response.SummaryResponse;
import com.example.barbersApp.service.AppointmentsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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
    private final ServiceRepository serviceRepository;
    private final ServicesInfoRepository servicesInfoRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Appointments getAppointmentsById(Long id) {
        Appointments appointments = appointmentRepository.findById(id).orElse(null);
        return appointments;
    }

    @Override
    public AppointmentsResponse createOrUpdateAppointments(AppointmentsRequest request) {
        AppointmentsResponse appointmentsResponse = new AppointmentsResponse();
        Appointments appointments = new Appointments();
        Appointments savedApointments = new Appointments();

        appointments = appointmentRepository.findAppointmentsWithParams(request.getBarberId(),request.getDate());
        if (appointments != null) {
            updateAppointmentsIfNeeded(request, appointments);
            savedApointments =appointmentRepository.save(appointments);

        } else {
            appointments = convertToAppointments(request);
            savedApointments = appointmentRepository.save(appointments);
        }
        

        for (Long serviceId : request.getServiceIds()){
            AppointmentSummary appointmentSummary = new AppointmentSummary();
            appointmentSummary.setAppointmentId(savedApointments.getId());
            Services services= new Services();
            services.setId(serviceId);
            appointmentSummary.setServices(services);
            appointmentSummary.setUserId(request.getUserId());
            appointmentSummary.setDate(request.getDate());
            appointmentSummary.setStatus(AppointmentStatus.PENDING);
            String timeField = request.getTimeFieldWithB();
            if (timeField != null) {
                appointmentSummary.setTime(timeField);
            }

            appointmentSummaryRepository.save(appointmentSummary);
        }



        appointmentsResponse.setAppointments(appointments);
   

        return appointmentsResponse;
    }

    private void updateAppointmentsIfNeeded(AppointmentsRequest request, Appointments appointments) {
        updateTFieldIfNeeded(appointments.getT1(), request.getT1(), newValue -> appointments.setT1(newValue));
        updateTFieldIfNeeded(appointments.getT2(), request.getT2(), newValue -> appointments.setT2(newValue));
        updateTFieldIfNeeded(appointments.getT3(), request.getT3(), newValue -> appointments.setT3(newValue));
        updateTFieldIfNeeded(appointments.getT4(), request.getT4(), newValue -> appointments.setT4(newValue));
        updateTFieldIfNeeded(appointments.getT5(), request.getT5(), newValue -> appointments.setT5(newValue));
        updateTFieldIfNeeded(appointments.getT6(), request.getT6(), newValue -> appointments.setT6(newValue));
        updateTFieldIfNeeded(appointments.getT7(), request.getT7(), newValue -> appointments.setT7(newValue));
        updateTFieldIfNeeded(appointments.getT8(), request.getT8(), newValue -> appointments.setT8(newValue));
        updateTFieldIfNeeded(appointments.getT9(), request.getT9(), newValue -> appointments.setT9(newValue));
        updateTFieldIfNeeded(appointments.getT10(), request.getT10(), newValue -> appointments.setT10(newValue));
        updateTFieldIfNeeded(appointments.getT11(), request.getT11(), newValue -> appointments.setT11(newValue));
        updateTFieldIfNeeded(appointments.getT12(), request.getT12(), newValue -> appointments.setT12(newValue));
    }
    
    private void updateTFieldIfNeeded(String currentValue, String newValue, SetterFunction setter) {
        if ("F".equals(currentValue) && "B".equals(newValue)) {
            setter.set("P");
        } else if (!"T".equals(currentValue)) {
            setter.set(newValue);
        }
    }
    
    
    @FunctionalInterface
    interface SetterFunction {
        void set(String value);
    }
    

    public static Appointments convertToAppointments(AppointmentsRequest request) {
        Appointments appointments = new Appointments();
        appointments.setBarberId(request.getBarberId());
        appointments.setDate(request.getDate());
        appointments.setT1(convertStatus(request.getT1()));
        appointments.setT2(convertStatus(request.getT2()));
        appointments.setT3(convertStatus(request.getT3()));
        appointments.setT4(convertStatus(request.getT4()));
        appointments.setT5(convertStatus(request.getT5()));
        appointments.setT6(convertStatus(request.getT6()));
        appointments.setT7(convertStatus(request.getT7()));
        appointments.setT8(convertStatus(request.getT8()));
        appointments.setT9(convertStatus(request.getT9()));
        appointments.setT10(convertStatus(request.getT10()));
        appointments.setT11(convertStatus(request.getT11()));
        appointments.setT12(convertStatus(request.getT12()));
        return appointments;
    }
    
    private static String convertStatus(String status) {
        return "B".equals(status) ? "P" : status;
    }

    @Override
    public AvaliableAppointmentHours getAvaliableAppointment(Long id, LocalDate date) {
        Appointments appointments = appointmentRepository.findByBarberIdAndDate(id,date);

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
            AvaliableAppointmentHours availableAppointmentHours = getAvaliableAppointment(barberId,date);
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
    @Override
    public List<CustomerSummaryResponse> getAppointmentSummary(Long userId, LocalDate date) {
        List<AppointmentSummary> summaries=appointmentSummaryRepository.findByUserIdAndDate(userId,date);
        
        
        List<Long> appointmentsIds=summaries.stream().map(AppointmentSummary::getAppointmentId)
        .collect(Collectors.toList());
        Long appointmentId=appointmentsIds.get(0);
        
        Appointments appointments=appointmentRepository.findById(appointmentId).orElse(null);

        Long barberId=appointments.getBarberId();
        Barber barber= barberRepository.findById(barberId).orElse(null);
        Customer customer= customerRepository.findById(userId).orElse(null);

        
        Map<String, List<AppointmentSummary>> groupByDateAndTime=summaries.stream()
        .collect(Collectors.groupingBy(summary->summary.getDate().toString()+summary.getTime()));

        List<CustomerSummaryResponse> response= new ArrayList<>();

        for(Map.Entry<String,List<AppointmentSummary>> entry : groupByDateAndTime.entrySet()){
            List<AppointmentSummary> appointmentSummaries= entry.getValue();
            if(!appointmentSummaries.isEmpty()){
                CustomerSummaryResponse customerResponse= new CustomerSummaryResponse();
                customerResponse.setBarberId(barberId);
                customerResponse.setDate(appointmentSummaries.get(0).getDate());
                customerResponse.setStatus(appointmentSummaries.get(0).getStatus());
                customerResponse.setTime(appointmentSummaries.get(0).getTime());

                String services= "";
                for (AppointmentSummary summary : entry.getValue()) {
                    ServicesInfo servicesInfo=servicesInfoRepository.findById(summary.getServices().getId()).orElse(null);
                    services +=(""+servicesInfo.getServices().getServiceName()+", ");
                }
                customerResponse.setServices(services);
                customerResponse.setAppId(appointments.getId());
                customerResponse.setBarberId(barberId);
                customerResponse.setUserId(userId);
                customerResponse.setBarberName(barber.getBarberName());
                String customerName=(" "+customer.getFirstName()+" "+customer.getLastName()+" ");
                customerResponse.setCustomerName(customerName);

                response.add(customerResponse);
            }
        }

        return response;
    }


    @Override
    public List<BarberSummaryResponse> getBarberAppointmentSummary(Long barberId, LocalDate date) {
        
        Appointments appointments= appointmentRepository.findByBarberIdAndDate(barberId,date);
        List<AppointmentSummary> summaries= appointmentSummaryRepository.findByAppointmentId(appointments.getId());
        

        Map<String, List<AppointmentSummary>> groupedByDateAndTime = summaries.stream()
        .collect(Collectors.groupingBy(summary -> summary.getDate().toString() + summary.getTime()));

        List<BarberSummaryResponse> response = new ArrayList<>();
        
       

        for (Map.Entry<String, List<AppointmentSummary>> entry : groupedByDateAndTime.entrySet()) {
            List<AppointmentSummary> appointmentSummaries = entry.getValue();
            if (!appointmentSummaries.isEmpty()) {
                BarberSummaryResponse barberResponse = new BarberSummaryResponse();
                barberResponse.setBarberId(barberId);
                barberResponse.setUserId(appointmentSummaries.get(0).getUserId()); // Assuming userId is same for each grouped appointment
                barberResponse.setDate(appointmentSummaries.get(0).getDate());
                barberResponse.setTime(appointmentSummaries.get(0).getTime());
                barberResponse.setStatus(appointmentSummaries.get(0).getStatus());


               String services="";
               for (AppointmentSummary summary : entry.getValue()) {
                    ServicesInfo servicesInfo=servicesInfoRepository.findById(summary.getServices().getId()).orElse(null);
                    
                    services += (""+servicesInfo.getServices().getServiceName()+" - "); 
               }
            
            barberResponse.setServices(services);
            barberResponse.setAppId(appointments.getId());
            response.add(barberResponse);
            }
        }
        return response;

        
    }

    @Override
    @Transactional
    public boolean updateAppointmentStatus(Long appointmentId, String time, String status) {
        Optional<Appointments> optionalAppointment = appointmentRepository.findById(appointmentId);
       
        if (optionalAppointment.isPresent()) {
            Appointments appointment = optionalAppointment.get();
            String newValue = "F";
            AppointmentStatus newStatus = AppointmentStatus.REJECTED;

            if ("ACCEPTED".equalsIgnoreCase(status)) {
                newValue = "T";
                newStatus = AppointmentStatus.ACCEPTED;
            }

            switch (time) {
                case "t1":
                    if ("P".equals(appointment.getT1())) appointment.setT1(newValue);
                    break;
                case "t2":
                    if ("P".equals(appointment.getT2())) appointment.setT2(newValue);
                    break;
                case "t3":
                    if ("P".equals(appointment.getT3())) appointment.setT3(newValue);
                    break;
                case "t4":
                    if ("P".equals(appointment.getT4())) appointment.setT4(newValue);
                    break;
                case "t5":
                    if ("P".equals(appointment.getT5())) appointment.setT5(newValue);
                    break;
                case "t6":
                    if ("P".equals(appointment.getT6())) appointment.setT6(newValue);
                    break;
                case "t7":
                    if ("P".equals(appointment.getT7())) appointment.setT7(newValue);
                    break;
                case "t8":
                    if ("P".equals(appointment.getT8())) appointment.setT8(newValue);
                    break;
                case "t9":
                    if ("P".equals(appointment.getT9())) appointment.setT9(newValue);
                    break;
                case "t10":
                    if ("P".equals(appointment.getT10())) appointment.setT10(newValue);
                    break;
                case "t11":
                    if ("P".equals(appointment.getT11())) appointment.setT11(newValue);
                    break;
                case "t12":
                    if ("P".equals(appointment.getT12())) appointment.setT12(newValue);
                    break;
                default:
                    return false;
            }

            appointmentRepository.save(appointment);

            // Update the status in AppointmentSummary
            List<AppointmentSummary> summaries = appointmentSummaryRepository.findByAppointmentId(appointmentId);
            for (AppointmentSummary summary : summaries) {
                String summaryTime=summary.getTime();

                if (summaryTime !=null && summaryTime.equals(time)) {
                    summary.setStatus(newStatus);
                    appointmentSummaryRepository.save(summary);
                }
            }

            return true;
        } else {
            return false;
        }
    }

   
}
