package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAppointments();
    public List<Appointment> getAppointmentsInDateRange(Date startDate, Date endDate);
    public Appointment insertAppointment(Appointment appointment);
}
