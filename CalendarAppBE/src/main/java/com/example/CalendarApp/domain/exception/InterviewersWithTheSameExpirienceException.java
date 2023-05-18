package com.example.CalendarApp.domain.exception;

public class InterviewersWithTheSameExpirienceException extends RuntimeException {

    private static final long serialVersionUID = 4383075957608176631L;

    public InterviewersWithTheSameExpirienceException(String message) {
        super(message);
    }
}
