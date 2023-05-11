package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment insertAppointment(Appointment appointment) {
        return appointmentRepository.insert(appointment);
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsInDateRange(Date startDate, Date endDate) {
        return appointmentRepository.findByDayBetweenOrderByDayAsc(startDate, endDate)
                .stream()
                .filter(byDayBetweenOrderByDayAsc1 ->
                        byDayBetweenOrderByDayAsc1.getDay().getHours() - 2 > 9
                && byDayBetweenOrderByDayAsc1.getDay().getHours() - 2 < 17
                && (byDayBetweenOrderByDayAsc1.getDay().getMinutes() == 0
                                || byDayBetweenOrderByDayAsc1.getDay().getMinutes() == 30))
                .collect(Collectors.toList());
    }
}
