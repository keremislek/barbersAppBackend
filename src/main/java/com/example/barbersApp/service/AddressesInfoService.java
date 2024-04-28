package com.example.barbersApp.service;

import java.util.List;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.request.AddressInfoCreateRequest;
import com.example.barbersApp.response.AddressInfoBarberIdResponse;
import com.example.barbersApp.response.AddressInfoResponse;

public interface AddressesInfoService {

    AdressesInfo createAddressInfo(AddressInfoCreateRequest request);

    AddressInfoResponse getAddressInfoById(Long id);

    List<AddressInfoBarberIdResponse> getAddressInfoByBarberId(Long id);

}
