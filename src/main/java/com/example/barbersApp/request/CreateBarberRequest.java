package com.example.barbersApp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBarberRequest {
	private String address;
	private String barberName;
	private String email;
	private String password;
	private String photoUrl;
}
