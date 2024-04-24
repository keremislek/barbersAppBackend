package com.example.barbersApp.service.impl;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Address;
import com.example.barbersApp.repository.AddressRepository;
import com.example.barbersApp.request.AddressCreateRequest;
import com.example.barbersApp.service.AddressService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    
    
    AddressRepository addressRepository;
    
    @Override
    public String createAddress(AddressCreateRequest request) {
        
        Address address=Address.builder()
        .fullAddress(request.getFullAddress())
        .build();

        addressRepository.save(address);

        throw new UnsupportedOperationException("Unimplemented method 'createAddress'");
    }

}
