package com.example.barbersApp.service.impl;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.BaseUserEntity;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private BarberRepository barberRepository;
    private CustomerRepository customerRepository;

    public CustomUserDetailsService(BarberRepository barberRepository, CustomerRepository customerRepository) {
        this.barberRepository = barberRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Attempt to find the user in either repository
        BaseUserEntity user = Stream.of(barberRepository.findByEmail(email), customerRepository.findByEmail(email))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user;
    }
}
