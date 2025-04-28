package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private int rate;
    private Long ratingId;
    private Long customerId;
    private String customerName;
    private Long barberId;
    private String barberName;
    
}
