package com.example.CalendarApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interviewer {

    @Transient
    public static final String SEQUENCE_NAME = "interviewer_sequence";

    @Id
    private int id;

    private String firstName;

    private String lastName;

    private boolean experienced;
}
