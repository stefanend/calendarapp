package com.example.CalendarApp.error;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import com.example.CalendarApp.domain.exception.InterviewersWithTheSameExpirienceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @Autowired
    private CalendarAppErrorFactory errorFactory;

    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error validationExceptionProgramNotFound(AppointmentNotFoundException e){
        return  errorFactory.createError(e);
    }

    @ExceptionHandler(InterviewersWithTheSameExpirienceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error validationInterviewersWithTheSameExpirienceException(InterviewersWithTheSameExpirienceException e){
        return  errorFactory.createError(e);
    }
}
