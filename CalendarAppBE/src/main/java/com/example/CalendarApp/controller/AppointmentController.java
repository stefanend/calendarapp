package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.service.AppointmentService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/interviews")
    public ResponseEntity<List<Appointment>> getInterviews() {
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @PostMapping("/insert")
    public ResponseEntity<Appointment> insertInterview(@RequestBody Appointment appointment) {
        appointment.setId(sequenceGeneratorService.getSequenceNumber(Appointment.SEQUENCE_NAME));
        return ResponseEntity.ok(appointmentService.insertAppointment(appointment));
    }
}
