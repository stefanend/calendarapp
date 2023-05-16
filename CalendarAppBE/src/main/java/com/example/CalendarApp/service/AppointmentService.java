package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAppointments();
    public List<Appointment> getAppointmentsInDateRange(Date startDate, Date endDate);
    public Appointment insertAppointment(Appointment appointment);
    public Appointment insertCandidate(int id, Candidate candidate);
    Appointment insertInterviewer(int id, Interviewer interviewer);
}
