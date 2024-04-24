package com.example.barbersApp.service.impl;


import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Customer;
import com.example.barbersApp.entities.Role;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CustomerRepository;
import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.RegisterRequest;
import com.example.barbersApp.response.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final BarberRepository barberRepository;



	public AuthenticationResponse authenticate(AuthenticationRequest request) throws NotFoundException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

         Customer customer = customerRepository.findByEmail(request.getEmail()).orElse(null);
        if (customer != null) {
            var jwtToken = jwtService.generateToken(customer);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            Barber barber = barberRepository.findByEmail(request.getEmail()).orElse(null);
            if (barber != null) {
                var jwtToken = jwtService.generateToken(barber);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
        }
        
        throw new NotFoundException();
				
	
    }

}
