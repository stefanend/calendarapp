package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.service.InterviewerService;
import com.example.CalendarApp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/interviewers")
@CrossOrigin(origins = "*")
public class InterviewerController {

    @Autowired
    private InterviewerService interviewerService;

    @Autowired
    private SequenceGeneratorService generatorService;

    @GetMapping
    public ResponseEntity<List<Interviewer>> getAllInterviewers() {
        return ResponseEntity.ok(interviewerService.getInterviewers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interviewer> getInterviewerById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(interviewerService.getInterviewerById(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Interviewer> addInterviewer(@RequestBody Interviewer interviewer) {
        interviewer.setId(generatorService.getSequenceNumber(Interviewer.SEQUENCE_NAME));
        return new ResponseEntity<>(interviewerService.saveInterviewer(interviewer), HttpStatus.CREATED);
    }
}
