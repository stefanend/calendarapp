package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Candidate;
import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.service.AppointmentService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getInterviews() {
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @GetMapping("/date")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(@RequestParam("startDate")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                        Date startDate,
                                                                        @RequestParam("endDate")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                        Date endDate) {
        return ResponseEntity.ok(appointmentService.getAppointmentsInDateRange(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<Appointment> insertAppointment(@RequestBody Appointment appointment) {
        appointment.setId(sequenceGeneratorService.getSequenceNumber(Appointment.SEQUENCE_NAME));
        return ResponseEntity.ok(appointmentService.insertAppointment(appointment));
    }

    @PostMapping("/candidate/{id}")
    public ResponseEntity<Appointment> insertCandidate(@PathVariable("id") int id, @RequestBody Candidate candidate) {
        return ResponseEntity.ok(appointmentService.insertCandidate(id, candidate));
    }

    @PostMapping("/interviewer/{id}")
    public ResponseEntity<Appointment> insertInterviewer(@PathVariable("id") int id,
                                                       @RequestBody Interviewer interviewer) {
        return ResponseEntity.ok(appointmentService.insertInterviewer(id, interviewer));
    }
}
