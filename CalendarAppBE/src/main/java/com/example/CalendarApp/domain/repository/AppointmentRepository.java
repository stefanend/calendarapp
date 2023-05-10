package com.example.CalendarApp.domain.repository;

import com.example.CalendarApp.domain.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, Integer> {
    @Query("{ $and: [{ 'day': { $gte: ?0 } }, { 'day': { $lt: ?1 } }] }")
    List<Appointment> findAppointmentsInDateRange(Date startDate, Date endDate);
}
