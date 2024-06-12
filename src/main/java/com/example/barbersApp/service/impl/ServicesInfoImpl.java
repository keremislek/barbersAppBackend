package com.example.barbersApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Services;
import com.example.barbersApp.entities.ServicesInfo;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.ServiceRepository;
import com.example.barbersApp.repository.ServicesInfoRepository;
import com.example.barbersApp.request.ServicesInfoCreateRequest;
import com.example.barbersApp.request.ServicesInfoUpdateRequest;
import com.example.barbersApp.response.ServicesInfoBarberIdResponse;
import com.example.barbersApp.response.ServicesInfoResponse;
import com.example.barbersApp.service.ServicesInfoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class ServicesInfoImpl implements ServicesInfoService{
   
   private final ServicesInfoRepository servicesInfoRepository;
   private final BarberRepository barberRepository;
   private final ServiceRepository serviceRepository;

    @Override
    public ServicesInfo createServicesInfo(ServicesInfoCreateRequest request) {
        ServicesInfo newServicesInfo=new ServicesInfo();
        var serviceInfo = servicesInfoRepository.findByBarberIdAndServicesId(request.getBarberId(), request.getServiceId())
        .orElse(newServicesInfo);

        serviceInfo.setPrice(request.getPrice());
        serviceInfo.setTime(request.getTime());
        Barber barber = barberRepository.findById(request.getBarberId())
            .orElseThrow(() -> new RuntimeException("Barber not found with id: " + request.getBarberId()));
        
        Services services = serviceRepository.findById(request.getServiceId())
            .orElseThrow(() -> new RuntimeException("Service not found with id: " + request.getServiceId()));

        serviceInfo.setBarber(barber);
        serviceInfo.setServices(services);

        ServicesInfo createdServicesInfo=servicesInfoRepository.save(serviceInfo);
        return createdServicesInfo;   
    }

    @Override
    public ServicesInfoResponse getServicesInfoById(Long id) {
        var servicesInfo=servicesInfoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Services info not found by id: "+id));        
        ServicesInfoResponse servicesInfoResponse=ServicesInfoResponse.builder()
        .price(servicesInfo.getPrice())
        .time(servicesInfo.getTime())
        .barberName(servicesInfo.getBarber().getBarberName())
        .servicesName(servicesInfo.getServices().getServiceName())
        .build();

       return servicesInfoResponse;
    }

    @Override
    public List<ServicesInfoBarberIdResponse> getServicesByBarberId(Long id) {
        List<ServicesInfo> serviceInfos=servicesInfoRepository.findByBarberId(id);
        return serviceInfos.stream().map(this::mapToServicesInfoResponse).collect(Collectors.toList());
        
        
    }

    private ServicesInfoBarberIdResponse mapToServicesInfoResponse(ServicesInfo servicesInfo){
        return ServicesInfoBarberIdResponse.builder().time(servicesInfo.getTime())
        .price(servicesInfo.getPrice())
        .serviceName(servicesInfo.getServices().getServiceName())
        .id(servicesInfo.getId())
        .build();
    }

    @Override
    @Transactional
    public void deleteServicesInfoById(Long id) {
        ServicesInfo serviceInfo=servicesInfoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ServicesInfo not found with id : "+id));
        servicesInfoRepository.delete(serviceInfo);
    }

    @Override
    public void updateServicesInfo(ServicesInfoUpdateRequest request) {
        ServicesInfo servicesInfo=servicesInfoRepository.findById(request.getId()).orElseThrow(() -> new EntityNotFoundException("ServicesInfo not found with id: " + request.getId()));
        servicesInfo.setPrice(request.getPrice());
        servicesInfo.setTime(request.getTime());
       
        servicesInfoRepository.save(servicesInfo);
    }

    
    
    

}
