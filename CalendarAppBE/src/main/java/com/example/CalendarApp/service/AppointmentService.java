package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAppointmentsInDateRange(LocalDateTime startDate, LocalDateTime endDate);
    public Appointment insertAppointment(Appointment appointment);
    public String deleteAppointment(int id);
    public Appointment insertCandidate(int id, Candidate candidate);
    Appointment insertInterviewer(int id, Interviewer interviewer);
}
