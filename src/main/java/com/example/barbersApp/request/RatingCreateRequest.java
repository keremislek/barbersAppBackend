package com.example.barbersApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RatingCreateRequest {
    
    private int rate;
    private Long customerId;
    private Long barberId;
}
