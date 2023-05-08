package com.example.CalendarApp.service;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Override
    public Appointment insertInterview(Appointment appointment) {
        return interviewRepository.insert(appointment);
    }

    @Override
    public List<Appointment> getInterviews() {
        return interviewRepository.findAll();
    }
}
