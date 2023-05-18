package com.example.CalendarApp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interviewer {

    @Transient
    public static final String SEQUENCE_NAME = "interviewer_sequence";

    @Id
    private int id;

    @NotEmpty(message = "First name is required!")
    @NotNull(message = "First name is required!")
    private String firstName;

    @NotEmpty(message = "Last name is required!")
    @NotNull(message = "Last name is required!")
    private String lastName;

    @NotNull(message = "Expirience is required!")
    private boolean experienced;
}
