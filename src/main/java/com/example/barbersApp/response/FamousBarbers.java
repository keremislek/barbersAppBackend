package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FamousBarbers {
    private Long barberId;
    private String photoUrl;
    private String barberName;
    private String barberAddress;
    private Double rate;
}
