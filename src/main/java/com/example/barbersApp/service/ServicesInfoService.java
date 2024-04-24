package com.example.barbersApp.service;

import java.util.List;

import com.example.barbersApp.entities.ServicesInfo;
import com.example.barbersApp.request.ServicesInfoCreateRequest;
import com.example.barbersApp.response.ServicesInfoBarberIdResponse;
import com.example.barbersApp.response.ServicesInfoResponse;

public interface ServicesInfoService {
    
    ServicesInfo createServicesInfo(ServicesInfoCreateRequest request);

    

    ServicesInfoResponse getServicesInfoById(Long id);



    List<ServicesInfoBarberIdResponse> getServicesByBarberId(Long id);

}
