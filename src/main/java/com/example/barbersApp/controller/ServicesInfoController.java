package com.example.barbersApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.entities.ServicesInfo;
import com.example.barbersApp.request.ServicesInfoCreateRequest;
import com.example.barbersApp.response.ServicesInfoBarberIdResponse;
import com.example.barbersApp.response.ServicesInfoResponse;
import com.example.barbersApp.service.ServicesInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/servicesInfo")
@CrossOrigin
public class ServicesInfoController {

    private ServicesInfoService servicesInfoService;

    public ServicesInfoController(ServicesInfoService servicesInfoService){
        this.servicesInfoService=servicesInfoService;
    }

    @PostMapping("/create")
    ResponseEntity<String> createServicesInfo(@RequestBody ServicesInfoCreateRequest request ){
        ServicesInfo servicesInfo=servicesInfoService.createServicesInfo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("ServicesInfo created with id: "+servicesInfo.getId());
    }

    @GetMapping("{id}")
    public ResponseEntity<ServicesInfoResponse> getById(@PathVariable Long id) {
        var servicesInfoResponse=servicesInfoService.getServicesInfoById(id);
        return ResponseEntity.ok().body(servicesInfoResponse);
    }

    @GetMapping("/barber/{id}")
    public List<ServicesInfoBarberIdResponse> getByBarberId(@PathVariable Long id) {
        return servicesInfoService.getServicesByBarberId(id);
        
        
    }
    
    
    
}
