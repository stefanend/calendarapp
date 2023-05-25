package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.service.AppointmentService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController controller;

    @Mock
    private AppointmentService service;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Test
    void insertAppointmentTest() {
        Appointment expected = new Appointment(1, LocalDateTime.now(), null, Collections.emptyList());

        when(service.insertAppointment(any(Appointment.class))).thenReturn(expected);

        ResponseEntity<Appointment> actual = controller.insertAppointment(expected);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void insertCandidateTest() {
        Candidate candidate = new Candidate(1,"Stefan", "Marjanovic");
        Appointment expected = new Appointment(1, LocalDateTime.now(), candidate, Collections.emptyList());

        when(service.insertCandidate(anyInt(), any(Candidate.class))).thenReturn(expected);

        ResponseEntity<Appointment> actual = controller.insertCandidate(1, candidate);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void getAppointmentsByDateRangeTest() {
        LocalDateTime day1 = LocalDateTime.of(2023, 5, 22, 11, 0);
        LocalDateTime day2 = LocalDateTime.of(2023, 5, 23, 9, 30);
        Appointment a1 = new Appointment(1, day1, null, List.of());
        Appointment a2 = new Appointment(2, day2, null, List.of());
        List<Appointment> appointments = List.of(a1, a2);
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 21, 9, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 5, 26, 17, 0);

        when(service.getAppointmentsInDateRange(startDate, endDate)).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = controller.getAppointmentsByDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(appointments.size(), response.getBody().size());
    }

    @Test
    void insertInterviewerTest() {
        Interviewer interviewer = new Interviewer();
        interviewer.setFirstName("Tatjana");
        interviewer.setLastName("Kunic");
        interviewer.setExperienced(false);
        Appointment expected = new Appointment(1, LocalDateTime.now(), null, List.of(interviewer));

        when(service.insertInterviewer(1, interviewer)).thenReturn(expected);
        when(sequenceGeneratorService.getSequenceNumber(Interviewer.SEQUENCE_NAME)).thenReturn(5);

        ResponseEntity<Appointment> actual = controller.insertInterviewer(1, interviewer);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertTrue(actual.hasBody());
        assertEquals(5, actual.getBody().getInterviewers().get(0).getId());
    }

    @Test
    void deleteAppointmentTest() {
        Appointment appointment = new Appointment(1, LocalDateTime.now(), null, List.of());
        String expected = "Appointment has been deleted!";

        when(service.deleteAppointment(appointment.getId())).thenReturn(expected);

        ResponseEntity<String> response = controller.deleteAppointment(1);

        verify(service, times(1)).deleteAppointment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expected, response.getBody());
    }
}
