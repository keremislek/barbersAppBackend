package com.example.barbersApp.service;

import java.util.List;

import com.example.barbersApp.request.AddressCreateRequest;
import com.example.barbersApp.response.DistrictResponse;

public interface AddressService {

    String createAddress(AddressCreateRequest request);

    List<DistrictResponse> getAllDistrictsName();

}
