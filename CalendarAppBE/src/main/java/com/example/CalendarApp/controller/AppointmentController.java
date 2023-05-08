package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.service.InterviewService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/interviews")
    public ResponseEntity<List<Appointment>> getInterviews() {
        return ResponseEntity.ok(interviewService.getInterviews());
    }

    @PostMapping("/insert")
    public ResponseEntity<Appointment> insertInterview(@RequestBody Appointment appointment) {
        appointment.setId(sequenceGeneratorService.getSequenceNumber(Appointment.SEQUENCE_NAME));
        return ResponseEntity.ok(interviewService.insertInterview(appointment));
    }
}
