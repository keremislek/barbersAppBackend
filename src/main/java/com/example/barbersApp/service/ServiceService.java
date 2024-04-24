package com.example.barbersApp.service;

import java.util.List;

import com.example.barbersApp.entities.Services;
import com.example.barbersApp.request.ServiceCreateRequest;
import com.example.barbersApp.response.ServicesResponse;

public interface ServiceService {

    Services createService(ServiceCreateRequest request);

    List<ServicesResponse> getAllServices();

}
