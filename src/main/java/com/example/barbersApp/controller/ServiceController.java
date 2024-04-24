package com.example.barbersApp.controller;

import java.security.Provider.Service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.barbersApp.entities.Services;
import com.example.barbersApp.request.ServiceCreateRequest;
import com.example.barbersApp.response.ServicesResponse;
import com.example.barbersApp.service.ServiceService;




@RestController
@RequestMapping("/services")
@CrossOrigin
public class ServiceController {

    private ServiceService serviceService;

    public ServiceController(ServiceService serviceService){
        this.serviceService=serviceService;
    }

    @PostMapping("/create")
    ResponseEntity<String> createService(@RequestBody ServiceCreateRequest request){
        Services createdService=serviceService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Services created with id: " + createdService.getId()); 
    } 
   
    @GetMapping
    public List<ServicesResponse> getAllServices() {
        return serviceService.getAllServices();
    }
    
}
