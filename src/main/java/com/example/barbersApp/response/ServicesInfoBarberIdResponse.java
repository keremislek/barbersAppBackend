package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicesInfoBarberIdResponse {
    private String serviceName;
    private int time;
    private double price;
}
