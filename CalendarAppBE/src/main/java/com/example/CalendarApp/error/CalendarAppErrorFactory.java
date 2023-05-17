package com.example.CalendarApp.error;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CalendarAppErrorFactory {

    private static final String APPOINTMENT_NOT_FOUND = "CA-4000";

    public Error createError(AppointmentNotFoundException e){
        return createError(HttpStatus.BAD_REQUEST.value(), APPOINTMENT_NOT_FOUND, e.getMessage());
    }

    private Error createError(int httpCode, String errorCode, String message){
        return Error.builder()
                .httpCode(httpCode)
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
