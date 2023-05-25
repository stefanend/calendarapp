package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.service.AppointmentService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
@Validated
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping()
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(@RequestParam("startDate")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                            LocalDateTime startDate,
                                                                        @RequestParam("endDate")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                        LocalDateTime endDate) {
        return ResponseEntity.ok(appointmentService.getAppointmentsInDateRange(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<Appointment> insertAppointment(@RequestBody @Valid Appointment appointment) {
        appointment.setId(sequenceGeneratorService.getSequenceNumber(Appointment.SEQUENCE_NAME));
        return ResponseEntity.ok(appointmentService.insertAppointment(appointment));
    }

    @PostMapping("/candidate/{id}")
    public ResponseEntity<Appointment> insertCandidate(@PathVariable("id") int id,
                                                       @RequestBody @Valid Candidate candidate) {
        return ResponseEntity.ok(appointmentService.insertCandidate(id, candidate));
    }

    @PostMapping("/interviewer/{id}")
    public ResponseEntity<Appointment> insertInterviewer(@PathVariable("id") int id,
                                                       @RequestBody @Valid Interviewer interviewer) {
        interviewer.setId(sequenceGeneratorService.getSequenceNumber(Interviewer.SEQUENCE_NAME));
        return ResponseEntity.ok(appointmentService.insertInterviewer(id, interviewer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") int id) {
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }
}
