package com.example.barbersApp.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Address;
import com.example.barbersApp.entities.District;
import com.example.barbersApp.repository.AddressRepository;
import com.example.barbersApp.repository.DistrictRepository;
import com.example.barbersApp.request.AddressCreateRequest;
import com.example.barbersApp.response.DistrictResponse;
import com.example.barbersApp.service.AddressService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    
    
    AddressRepository addressRepository;
    @Autowired
    DistrictRepository districtRepository;
    
    @Override
    public String createAddress(AddressCreateRequest request) {
        
        Address address=Address.builder()
        .fullAddress(request.getFullAddress())
        .build();

        addressRepository.save(address);

        throw new UnsupportedOperationException("Unimplemented method 'createAddress'");
    }

    @Override
    public List<DistrictResponse> getAllDistrictsName() {
        List<District> districts=districtRepository.findAll();
        List<DistrictResponse> districtResponses = new ArrayList<>();

    for (District district : districts) {
        DistrictResponse response = new DistrictResponse();
        response.setId(district.getId());
        response.setName(district.getName());
        districtResponses.add(response);
    }
        
    return districtResponses;
   
    }

  

}
