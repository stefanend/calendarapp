package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Interviewer;
import com.example.CalendarApp.domain.repository.InterviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InterviewerServiceImpl implements InterviewerService{

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Override
    public List<Interviewer> getInterviewers() {
        return interviewerRepository.findAll();
    }

    @Override
    public Interviewer getInterviewerById(int id) throws NoSuchElementException {
        return interviewerRepository.findById(id).orElseThrow();
    }

    @Override
    public Interviewer saveInterviewer(Interviewer interviewer) {
        return interviewerRepository.save(interviewer);
    }
}
