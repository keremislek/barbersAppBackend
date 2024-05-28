package com.example.barbersApp.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsRequest {
    private Long userId;
    private Long barberId;
    private LocalDate date;
    private List<Long> serviceIds;
    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String t9;
    private String t10;
    private String t11;
    private String t12;



    public String getTimeFieldWithB() {
        if ("B".equals(t1)) return "t1";
        if ("B".equals(t2)) return "t2";
        if ("B".equals(t3)) return "t3";
        if ("B".equals(t4)) return "t4";
        if ("B".equals(t5)) return "t5";
        if ("B".equals(t6)) return "t6";
        if ("B".equals(t7)) return "t7";
        if ("B".equals(t8)) return "t8";
        if ("B".equals(t9)) return "t9";
        if ("B".equals(t10)) return "t10";
        if ("B".equals(t11)) return "t11";
        if ("B".equals(t12)) return "t12";
        return null; // B değeri bulunmazsa null döner
    }

}
