package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.exception.AppointmentNotFoundException;
import com.example.CalendarApp.domain.exception.InterviewersWithTheSameExpirienceException;
import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.domain.repository.AppointmentRepository;
import com.example.CalendarApp.domain.repository.InterviewerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    void getAppointmentsTest() {
        Appointment a1 = new Appointment(1, now(), null, List.of());
        Appointment a2 = new Appointment(2, now(), null, List.of());
        Appointment a3 = new Appointment(3, now(), null, List.of());
        List<Appointment> expected = List.of(a1, a2, a3);

        when(appointmentRepository.findAll()).thenReturn(expected);

        List<Appointment> actual = service.getAppointments();

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    void getAppointmentsInDateRangeTest() {
        LocalDateTime day1 = LocalDateTime.of(2023, 5, 25, 18, 0);
        LocalDateTime day2 = LocalDateTime.of(2023, 5, 23, 11, 10);
        LocalDateTime day3 = LocalDateTime.of(2023, 5, 22, 8, 0);
        LocalDateTime day4 = LocalDateTime.of(2023, 5, 22, 12, 30);
        Appointment a1 = new Appointment(1, day1, null, List.of());
        Appointment a2 = new Appointment(2, day2, null, List.of());
        Appointment a3 = new Appointment(3, day3, null, List.of());
        Appointment a4 = new Appointment(4, day4, null, List.of());
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 20, 9, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 5, 27, 17, 0);
        List<Appointment> appointments = List.of(a1, a2, a3, a4);

        when(appointmentRepository.findByDayBetweenOrderByDayAsc(startDate, endDate)).thenReturn(appointments);

        List<Appointment> result = service.getAppointmentsInDateRange(startDate, endDate);
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getId());
    }

    @Test
    void insertInterviewerTest() {
        Interviewer interviewer = new Interviewer(1, "Stefan", "Marjanovic", true);
        List<Interviewer> interviewers = new ArrayList<>();
        interviewers.add(interviewer);
        Appointment expected = new Appointment(1, now(), null, interviewers);

        when(appointmentRepository.findById(anyInt())).thenReturn(Optional.of(expected));
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

    @Test
    void insertCandidateTest() {
        Appointment appointment = new Appointment(1, now(), null, List.of());
        Candidate candidate = new Candidate(1, "Tatjana", "Kunic");

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(appointment));

        assertNotNull(appointment.getCandidate());
        assertEquals(candidate, appointment.getCandidate());
    }

    @Test
    void insertCandidateWhenAppointmentWithGivenIdDoesNotExistTest() {
        Appointment appointment = new Appointment(11, now(), null, List.of());
        Candidate candidate = new Candidate(1, "Tatjana", "Kunic");

        Exception e = assertThrows(AppointmentNotFoundException.class, () -> service.insertCandidate(appointment.getId(), candidate));
        assertThat(e).hasMessage(String.format("Appointment with the id: %d does not exist!", appointment.getId()));
    }

    @Test
    void deleteAppointmentTest() {
        Appointment appointment = new Appointment(1, now(), null, List.of());
        String expected = "Appointment has been deleted!";

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        String actual = service.deleteAppointment(appointment.getId());

        verify(appointmentRepository, times(1)).deleteById(appointment.getId());
        assertEquals(expected, actual);
    }

    @Test
    void deleteAppointmentWhenAppointmentWithGivenIdDoesNotExistTest() {
        Appointment appointment = new Appointment(11, now(), null, List.of());

        Exception e = assertThrows(AppointmentNotFoundException.class, () -> service.deleteAppointment(appointment.getId()));
        assertThat(e).hasMessage(String.format("Appointment with the id: %d does not exist!", appointment.getId()));
    }
}
