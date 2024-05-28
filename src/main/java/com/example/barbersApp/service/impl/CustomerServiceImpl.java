package com.example.barbersApp.service.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.barbersApp.entities.Customer;
import com.example.barbersApp.entities.Role;
import com.example.barbersApp.repository.CustomerRepository;
import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateCustomerRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.CustomerDetailResponse;
import com.example.barbersApp.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;



@Service
public class CustomerServiceImpl implements CustomerService{
	
	
	 CustomerRepository customerRepository;
	 PasswordEncoder passwordEncoder;
	 JwtService jwtService;
	 AuthenticationManager authenticationManager;
	
	public CustomerServiceImpl(CustomerRepository customerRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
		this.customerRepository=customerRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
		this.authenticationManager=authenticationManager;
	}

	@Override
	public AuthenticationResponse register(CreateCustomerRequest request) {
		var customer=Customer.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.Customer)
				.build();
		customerRepository.save(customer);
		var jwtToken = jwtService.generateToken(customer);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.id(customer.getId())
				.build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		var user =customerRepository.findByEmail(request.getEmail()).get();
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public CustomerDetailResponse getCustomerById(Long id) {
		var customer=customerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Customer not found by id : "+id));
		
		CustomerDetailResponse customerDetailResponse=CustomerDetailResponse.builder()
		.id(customer.getId())
		.firstName(customer.getFirstName())
		.lastName(customer.getLastName())
		.build();

		return customerDetailResponse;

	}

	@Override
	public void deleteCustomerById(Long id) {
		if(!customerRepository.existsById(id)){
			throw new UnsupportedOperationException("Unimplemented method 'deleteCustomerById'");
		}
		customerRepository.deleteById(id);
	}

	@Override
	public void updateCustomerEmailAndPassword(Long id, String email, String password) {
		Customer customer=customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer not found by id : "+id));
		customer.setEmail(email);
		customer.setPassword(passwordEncoder.encode(password));
		customerRepository.save(customer);
	}



}
