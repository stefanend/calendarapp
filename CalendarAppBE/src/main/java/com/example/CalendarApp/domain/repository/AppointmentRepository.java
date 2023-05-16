package com.example.CalendarApp.domain.repository;

import com.example.CalendarApp.domain.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment, Integer> {
    List<Appointment> findByDayBetweenOrderByDayAsc(Date startDate, Date endDate);
    Optional<Appointment> findById(int id);
}
