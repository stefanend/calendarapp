package com.example.CalendarApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private int id;

    private Date day;

    private Candidate candidate;

    private List<Interviewer> interviewers;
}
