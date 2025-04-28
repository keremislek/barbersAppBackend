package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfoBarberIdResponse {
    private String fullAddress;
    private String district;
}
