package com.example.barbersApp.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return AppointmentStatus.fromValue(dbData);
    }
}
