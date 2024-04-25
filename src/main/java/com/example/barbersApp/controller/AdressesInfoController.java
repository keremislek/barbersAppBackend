package com.example.barbersApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.request.AddressInfoCreateRequest;
import com.example.barbersApp.service.AddressesInfoService;

@Controller
@RequestMapping("/addressesInfo")
public class AdressesInfoController {

    private AddressesInfoService addressesInfoService;

    public AdressesInfoController(AddressesInfoService addressesInfoService){
        this.addressesInfoService=addressesInfoService;
    }

    @PostMapping("/create")
    ResponseEntity<String> createAddressInfo(@RequestBody AddressInfoCreateRequest request){
        AdressesInfo adressesInfo=addressesInfoService.createAddressInfo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Adres Info created with id: "+adressesInfo.getId()); 
    }
    

   
    

}
