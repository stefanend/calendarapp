package com.example.CalendarApp.domain.repository;

import com.example.CalendarApp.domain.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterviewRepository extends MongoRepository<Appointment, Integer> {
}
