package com.example.CalendarApp.error;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import com.example.CalendarApp.domain.exception.InterviewersWithTheSameExpirienceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CalendarAppErrorFactory {

    private static final String APPOINTMENT_NOT_FOUND = "CA-4000";
    private static final String INTERVIEWERS_WITH_THE_SAME_EXPIRIENCE = "CA-4001";

    public Error createError(AppointmentNotFoundException e){
        return createError(HttpStatus.NOT_FOUND.value(), APPOINTMENT_NOT_FOUND, e.getMessage());
    }

    public Error createError(InterviewersWithTheSameExpirienceException e){
        return createError(HttpStatus.BAD_REQUEST.value(), INTERVIEWERS_WITH_THE_SAME_EXPIRIENCE, e.getMessage());
    }

    private Error createError(int httpCode, String errorCode, String message){
        return Error.builder()
                .httpCode(httpCode)
                .errorCode(errorCode)
                .message(message)
                .build();
    }
}
