package com.example.barbersApp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.barbersApp.request.CreateCustomerRequest;
import com.example.barbersApp.response.AuthenticationResponse;
import com.example.barbersApp.response.CustomerDetailResponse;
import com.example.barbersApp.service.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {
	
	 private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService=customerService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody CreateCustomerRequest request) {
	
		return ResponseEntity.ok(customerService.register(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDetailResponse> getCustomerById(@PathVariable Long id){
		var customerResponse=customerService.getCustomerById(id);
		return ResponseEntity.ok().body(customerResponse);
	}


	
	
}
