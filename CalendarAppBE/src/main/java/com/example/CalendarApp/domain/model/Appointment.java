package com.example.CalendarApp.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Date is required!")
    private Date day;

    private Candidate candidate;

    @Size(max = 2, message = "The maximum number of interviewers per appointment is two!")
    private List<Interviewer> interviewers;
}
