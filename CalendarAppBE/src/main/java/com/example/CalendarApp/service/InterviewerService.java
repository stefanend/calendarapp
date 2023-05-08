package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Interviewer;

import java.util.List;

public interface InterviewerService {
    List<Interviewer> getInterviewers();
    Interviewer getInterviewerById(int id);
    Interviewer saveInterviewer(Interviewer interviewer);
}
