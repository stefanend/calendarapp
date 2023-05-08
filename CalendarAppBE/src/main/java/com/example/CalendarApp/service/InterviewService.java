package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;

import java.util.List;

public interface InterviewService {

    public List<Appointment> getInterviews();
    public Appointment insertInterview(Appointment appointment);
}
