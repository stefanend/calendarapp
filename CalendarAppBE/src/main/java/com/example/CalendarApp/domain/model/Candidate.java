package com.example.CalendarApp.domain.model;

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

    private String firstName;

    private String lastName;
}
