package com.example.barbersApp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {
    Long id;
    Long appointmentId;
    Long serviceId;
    Long userId;
}
