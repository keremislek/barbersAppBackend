package com.example.barbersApp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateBarberRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberResponse;
import com.example.barbersApp.response.BarberTop5Query;
import com.example.barbersApp.response.FamousBarbers;


public interface BarberService {


    Object getSearchByName = null;

	AuthenticationResponse register(CreateBarberRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

 

    List<BarberResponse> getAllBarbers();

    BarberDetailResponse getBarberById(Long id);

    BarberResponse getBarberByEmail(String email);

    List<FamousBarbers> getFamousBarberByRating();

    List<BarberDetailResponse> getBySearchName(String name);


}
