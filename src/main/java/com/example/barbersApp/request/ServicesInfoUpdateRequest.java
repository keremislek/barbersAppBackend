package com.example.barbersApp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicesInfoUpdateRequest {
    private double price;
    private int time;
    private Long barberId;
    private Long serviceId;
    private Long id;
}
