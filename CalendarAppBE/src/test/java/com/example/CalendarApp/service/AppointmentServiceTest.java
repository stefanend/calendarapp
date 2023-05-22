package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import com.example.CalendarApp.domain.exception.InterviewersWithTheSameExpirienceException;
import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.domain.repository.AppointmentRepository;
import com.example.CalendarApp.domain.repository.InterviewerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.time.LocalDateTime.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentServiceImpl service;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private InterviewerRepository interviewerRepository;

    @Test
    void insertAppointmentTest() {
        Appointment expected = new Appointment(1, now(), null, Collections.emptyList());

        when(appointmentRepository.insert(ArgumentMatchers.any(Appointment.class))).thenReturn(expected);

        Appointment actual = service.insertAppointment(new Appointment(2, now(), null,
                Collections.emptyList()));

        assertEquals(expected, actual);
    }

    @Test
    void insertAppointmentExceptionTest() {
        List<Interviewer> interviewerList = Arrays.asList
                (new Interviewer(1, "Stefan", "Marjanovic", true),
                        (new Interviewer(2, "Tatjana", "Kunic", true)));

        Appointment appointment = new Appointment(1, now(), null, interviewerList);

        Exception exception = assertThrows(InterviewersWithTheSameExpirienceException.class,
                () -> service.insertAppointment(appointment));

        assertThat(exception).hasMessage("There can't be multiple interviewers with the same expirience per appointment!");
    }

    @Test
    void insertInterviewerTest() {
        Interviewer interviewer = new Interviewer(1, "Stefan", "Marjanovic", true);
        List<Interviewer> interviewers = new ArrayList();
        interviewers.add(interviewer);
        Appointment expected = new Appointment(1, now(), null, interviewers);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(expected));
        when(appointmentRepository.save(expected)).thenReturn(expected);

        Appointment actual = service.insertInterviewer(1, interviewer);

        assertEquals(expected, actual);
    }

    @Test
    void insertInterviewerExceptionTest() {
        Exception exception = assertThrows(AppointmentNotFoundException.class,
                () -> service.insertInterviewer(1, new Interviewer()));

        assertThat(exception).hasMessage(String.format("Appointment with the id: %d does not exist!", 1));
    }
}
