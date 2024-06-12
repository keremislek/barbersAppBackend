package com.example.barbersApp.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.barbersApp.request.AuthenticationRequest;
import com.example.barbersApp.request.CreateBarberRequest;
import com.example.barbersApp.request.UpdateEmailAndPassword;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.BarberDetailResponse;
import com.example.barbersApp.response.BarberResponse;
import com.example.barbersApp.response.FamousBarbers;
import com.example.barbersApp.service.BarberService;



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

	@PutMapping("/{id}/photo")
	public ResponseEntity<Void> updateBarberPhoto(@PathVariable Long id, @RequestBody String photoUrl){
		barberService.updateBarberPhoto(id,photoUrl);
		return ResponseEntity.noContent().build();
	}
	

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody CreateBarberRequest request ) {
		
		return ResponseEntity.ok(barberService.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(barberService.authenticate(request));
	}

	@GetMapping("/famous")
	public ResponseEntity<List<FamousBarbers>> getFamousBarberByRating(){
		return ResponseEntity.ok().body(barberService.getFamousBarberByRating());
	}

	@GetMapping("/search")
	public ResponseEntity<List<BarberDetailResponse>> getSearchByName(@RequestParam String name){
		return ResponseEntity.ok().body(barberService.getBySearchName(name));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBarber(@PathVariable Long id){
		barberService.deleteBarberById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateBarberEmailAndPassword(@PathVariable Long id, @RequestBody UpdateEmailAndPassword request){
		barberService.updateBarberEmailAndPassword(id,request.getEmail(),request.getPassword());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/name")
		public ResponseEntity<Void> updateBarberName(@PathVariable Long id, @RequestBody String name){
			barberService.updateBarberName(id,name);
			return ResponseEntity.noContent().build();

		}
	}
	

	
	
	

