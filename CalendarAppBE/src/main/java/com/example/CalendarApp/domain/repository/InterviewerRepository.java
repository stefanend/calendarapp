package com.example.CalendarApp.domain.repository;

import com.example.CalendarApp.domain.model.Interviewer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterviewerRepository extends MongoRepository<Interviewer, Integer> {
}
