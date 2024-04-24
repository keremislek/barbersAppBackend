package com.example.barbersApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbersApp.request.AddressCreateRequest;
import com.example.barbersApp.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressController {


    private AddressService addressService;
    
    public AddressController(AddressService addressService){
        this.addressService=addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAddress(@RequestBody AddressCreateRequest request){
        return ResponseEntity.ok(addressService.createAddress(request));
    }
}
