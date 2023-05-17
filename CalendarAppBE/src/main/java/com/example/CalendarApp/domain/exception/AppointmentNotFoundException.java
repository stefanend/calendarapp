package com.example.CalendarApp.domain.exception;

public class AppointmentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8982302418776059844L;

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
