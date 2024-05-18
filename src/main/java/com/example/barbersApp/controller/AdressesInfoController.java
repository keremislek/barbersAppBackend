package com.example.barbersApp.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.request.AddressInfoCreateRequest;
import com.example.barbersApp.response.AddressInfoBarberIdResponse;
import com.example.barbersApp.response.AddressInfoResponse;
import com.example.barbersApp.service.AddressesInfoService;

@Controller
@CrossOrigin
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
    
    @GetMapping("{id}")
    public ResponseEntity<AddressInfoResponse> getById(@PathVariable Long id){
        AddressInfoResponse addressInfoResponse=addressesInfoService.getAddressInfoById(id);
        return ResponseEntity.ok().body(addressInfoResponse);
    }
    
    @GetMapping("/barber/{id}")
    public List<AddressInfoBarberIdResponse> getByBarberId(@PathVariable Long id){
        return addressesInfoService.getAddressInfoByBarberId(id);
    }

}
