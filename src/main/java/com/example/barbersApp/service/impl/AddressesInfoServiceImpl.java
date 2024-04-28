package com.example.barbersApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.District;
import com.example.barbersApp.repository.AddressesInfoRepository;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.DistrictRepository;
import com.example.barbersApp.request.AddressInfoCreateRequest;
import com.example.barbersApp.response.AddressInfoBarberIdResponse;
import com.example.barbersApp.response.AddressInfoResponse;
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

    @Override
    public AddressInfoResponse getAddressInfoById(Long id) {
        var addressInfo=addressesInfoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Address Not found by id: "+id));
        AddressInfoResponse addressInfoResponse=AddressInfoResponse.builder()
        .barberName(addressInfo.getBarber().getBarberName())
        .districtName(addressInfo.getDistrict().getName())
        .fullAddress(addressInfo.getFullAddress())
        .build();

        return addressInfoResponse;
        
    }

    @Override
    public List<AddressInfoBarberIdResponse> getAddressInfoByBarberId(Long id) {
        List<AdressesInfo> adressesInfos=addressesInfoRepository.findByBarberId(id);
        return adressesInfos.stream().map(this::mapToAddressInfoResponse).collect(Collectors.toList());
    }

    private AddressInfoBarberIdResponse mapToAddressInfoResponse(AdressesInfo adressesInfo){
        return AddressInfoBarberIdResponse.builder()
        .district(adressesInfo.getDistrict().getName())
        .fullAddress(adressesInfo.getFullAddress())
        .build();
    }

}
