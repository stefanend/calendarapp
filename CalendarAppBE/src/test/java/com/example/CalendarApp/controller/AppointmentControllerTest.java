package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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
}
