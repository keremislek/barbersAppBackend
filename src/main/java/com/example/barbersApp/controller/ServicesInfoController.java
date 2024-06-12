package com.example.barbersApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.entities.ServicesInfo;
import com.example.barbersApp.request.ServicesInfoCreateRequest;
import com.example.barbersApp.request.ServicesInfoUpdateRequest;
import com.example.barbersApp.response.ServicesInfoBarberIdResponse;
import com.example.barbersApp.response.ServicesInfoResponse;
import com.example.barbersApp.service.ServicesInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/servicesInfo")
@CrossOrigin
public class ServicesInfoController {

    private ServicesInfoService servicesInfoService;

    public ServicesInfoController(ServicesInfoService servicesInfoService){
        this.servicesInfoService=servicesInfoService;
    }
   
    @GetMapping("/barber/{id}")
    public List<ServicesInfoBarberIdResponse> getByBarberId(@PathVariable Long id) {
        return servicesInfoService.getServicesByBarberId(id);
        
        
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        servicesInfoService.deleteServicesInfoById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Services Info deleted with id : "+id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateServicesInfo(@RequestBody ServicesInfoUpdateRequest request){
    servicesInfoService.updateServicesInfo(request);
    return ResponseEntity.status(HttpStatus.OK).body("ServicesInfo updated "+request.getId()); 
    }
    
    
}
