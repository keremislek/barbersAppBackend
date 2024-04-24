package com.example.barbersApp.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.barbersApp.entities.Barber;
import com.example.barbersApp.entities.Role;
import com.example.barbersApp.repository.BarberRepository;
import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateBarberRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberResponse;
import com.example.barbersApp.service.BarberService;

import jakarta.persistence.EntityNotFoundException;



@Service
public class BarberServiceImpl implements BarberService {

	 BarberRepository barberRepository;
	 PasswordEncoder passwordEncoder;
	 JwtService jwtService;
	 AuthenticationManager authenticationManager;

	
	public BarberServiceImpl(BarberRepository barberRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager) {
		this.barberRepository=barberRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
		this.authenticationManager=authenticationManager;
	}

	@Override
	public AuthenticationResponse register(CreateBarberRequest request) {
		Barber barber=Barber.builder()
		.address(request.getAddress())
		.barberName(request.getBarberName())
		.email(request.getEmail())
		.password(passwordEncoder.encode(request.getPassword()))//passwordEncoder.encode(request.getPassword()))
		.role(Role.Barber)
		.photoUrl(request.getPhotoUrl())
		.build();
		barberRepository.save(barber);
		var jwtToken=jwtService.generateToken(barber);
		
		return AuthenticationResponse.builder().token(jwtToken).id(barber.getId()).build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);

		var user= barberRepository.findByEmail(request.getEmail()).get();
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	

	@Override
	public List<BarberResponse> getAllBarbers() {
		List<Barber> barbers= barberRepository.findAll();
		return barbers.stream().map(this::mapToBarberResponse).collect(Collectors.toList());
	}

	private BarberResponse mapToBarberResponse(Barber barber){
		return BarberResponse.builder().barberName(barber.getBarberName())
		.id(barber.getId())
		.address(barber.getAddress()).build();
	}

	@Override
	public BarberDetailResponse getBarberById(Long id) {
		var barber=barberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("barber not found by id: "+id));
	
		BarberDetailResponse barberDetailResponse=BarberDetailResponse.builder()
		.id(id)
		.address(barber.getAddress())
		.barberName(barber.getBarberName())
		.services(barber.getServices())
		.photoUrl(barber.getPhotoUrl())
		.build();

		return barberDetailResponse;
		
	}

	@Override
	public BarberResponse getBarberByEmail(String email) {
		Barber barber=barberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("barber not found by email : "+email));
		BarberResponse barberResponse=BarberResponse.builder()
		.address(barber.getAddress())
		.barberName(barber.getBarberName())
		.id(barber.getId())
		.build();

		return barberResponse;
	}

	

}
