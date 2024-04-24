package com.example.barbersApp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import com.example.barbersApp.entities.Services;
import com.example.barbersApp.repository.ServiceRepository;
import com.example.barbersApp.request.ServiceCreateRequest;
import com.example.barbersApp.response.ServicesResponse;
import com.example.barbersApp.service.ServiceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public Services createService(ServiceCreateRequest request) {
       Services newService=new Services();
       newService.setServiceName(request.getServiceName());
       Services createdServices=serviceRepository.save(newService);
      
       return createdServices;
        
    }

    @Override
    public List<ServicesResponse> getAllServices() {
        List<Services> services= serviceRepository.findAll();
        return services.stream().map(this::mapToServicesResponse).collect(Collectors.toList());
        
        
    }
    private ServicesResponse mapToServicesResponse (Services services){
        return ServicesResponse.builder().serviceName(services.getServiceName())
        .id(services.getId()).build();
    }
    
}
