package com.example.barbersApp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Long;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryRequest {
    Long appointmentId;
    Long userId;
}
