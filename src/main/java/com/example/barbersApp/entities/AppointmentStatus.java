package com.example.barbersApp.entities;

public enum AppointmentStatus {
    PENDING(1),
    ACCEPTED(2),
    REJECTED(3),
    CANCELED(4);

    private final int value;

    AppointmentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AppointmentStatus fromValue(int value) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
