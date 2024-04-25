package com.example.barbersApp.service;

import com.example.barbersApp.entities.AdressesInfo;
import com.example.barbersApp.request.AddressInfoCreateRequest;

public interface AddressesInfoService {

    AdressesInfo createAddressInfo(AddressInfoCreateRequest request);

}
