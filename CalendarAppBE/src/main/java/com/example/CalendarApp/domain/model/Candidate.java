package com.example.CalendarApp.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    private int id;

    @NotEmpty(message = "First name is required!")
    @NotNull(message = "First name is required!")
    private String firstName;

    @NotEmpty(message = "Last name is required!")
    @NotNull(message = "Last name is required!")
    private String lastName;
}
