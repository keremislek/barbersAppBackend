package com.example.barbersApp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateBarberRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberResponse;

import com.example.barbersApp.service.BarberService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/barbers")
@CrossOrigin
public class BarberController {
	
	private BarberService barberService;
	
	public BarberController(BarberService barberService) {
		this.barberService=barberService;
	}

	@GetMapping
	public List<BarberResponse> getAllBarbers(){
		return barberService.getAllBarbers();
	}
	@GetMapping("/email/{email}")
	public ResponseEntity<BarberResponse> getBarberByEmail(@PathVariable String email){
		var barberResponse=barberService.getBarberByEmail(email);
		return ResponseEntity.ok().body(barberResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BarberDetailResponse> getBarberById(@PathVariable Long id){
		var barberResponse=barberService.getBarberById(id);
		return ResponseEntity.ok().body(barberResponse);
	}
	

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody CreateBarberRequest request ) {
		
		return ResponseEntity.ok(barberService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(barberService.authenticate(request));
	}

	
	
	
	
}
