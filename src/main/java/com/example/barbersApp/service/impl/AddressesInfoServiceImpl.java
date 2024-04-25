package com.example.barbersApp.service.impl;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.District;
import com.example.barbersApp.repository.AddressesInfoRepository;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.DistrictRepository;
import com.example.barbersApp.request.AddressInfoCreateRequest;
import com.example.barbersApp.service.AddressesInfoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressesInfoServiceImpl implements AddressesInfoService{
    
    private final AddressesInfoRepository addressesInfoRepository;
    private final BarberRepository barberRepository;
    private final DistrictRepository districtRepository;

    @Override
    public AdressesInfo createAddressInfo(AddressInfoCreateRequest request) {
        
        AdressesInfo newAdressesInfo= new AdressesInfo();
        newAdressesInfo.setFullAddress(request.getFullAddress());

        Barber barber=barberRepository.findById(request.getBarberId()).orElseThrow(() ->new RuntimeException("barber not found by id : "+request.getBarberId()));
        District district=districtRepository.findById(request.getDistrictId()).orElseThrow(() ->new RuntimeException("ditrict not found by id: "+request.getDistrictId()));
        
        newAdressesInfo.setBarber(barber);
        newAdressesInfo.setDistrict(district);

        AdressesInfo createdAddressInfo=addressesInfoRepository.save(newAdressesInfo);
        return createdAddressInfo;
    }

}
