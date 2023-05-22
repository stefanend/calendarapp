package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import com.example.CalendarApp.domain.exception.InterviewersWithTheSameExpirienceException;
import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.domain.repository.AppointmentRepository;
import com.example.CalendarApp.domain.repository.InterviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Override
    public Appointment insertAppointment(Appointment appointment) {
        long count = appointment.getInterviewers().stream()
                .filter(Interviewer::isExperienced)
                .count();

        if(count == 2) {
            throw new InterviewersWithTheSameExpirienceException("There can't be multiple interviewers with the same expirience per appointment!");
        }

        return appointmentRepository.insert(appointment);
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByDayBetweenOrderByDayAsc(startDate, endDate)
                .stream()
                .filter(byDayBetweenOrderByDayAsc1 ->
                        byDayBetweenOrderByDayAsc1.getDay().getHour() >= 9
                && byDayBetweenOrderByDayAsc1.getDay().getHour() <= 16
                && (byDayBetweenOrderByDayAsc1.getDay().getMinute() == 0
                                || byDayBetweenOrderByDayAsc1.getDay().getMinute() == 30))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment insertCandidate(int id, Candidate candidate) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(format("Appointment with the id: %d does not exist!", id))));

        appointment.get().setCandidate(candidate);

        return appointmentRepository.save(appointment.get());
    }

    @Override
    public Appointment insertInterviewer(int id, Interviewer interviewer) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(format("Appointment with the id: %d does not exist!", id))));

        List<Interviewer> interviewers = appointment.get()
                .getInterviewers();

        interviewers.stream()
                .filter(i -> i.isExperienced() == interviewer.isExperienced())
                .findAny()
                .ifPresent(i -> {
                    interviewerRepository.delete(i);
                    interviewers.remove(i);
                });

        interviewers.add(interviewerRepository.save(interviewer));
        appointment.get().setInterviewers(interviewers);

        return appointmentRepository.save(appointment.get());
    }

    @Override
    public String deleteAppointment(int id) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(format("Appointment with the id: %d does not exist!", id))));

        appointmentRepository.deleteById(appointment.get().getId());

        return "Appointment has been deleted!";
    }
}
