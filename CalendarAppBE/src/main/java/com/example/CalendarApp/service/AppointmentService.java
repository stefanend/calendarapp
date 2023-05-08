package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;

import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAppointments();
    public Appointment insertAppointment(Appointment appointment);
}
