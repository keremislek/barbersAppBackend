package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoResponse {
    private String fullAddress;
    private String barberName;
    private String districtName;
}
