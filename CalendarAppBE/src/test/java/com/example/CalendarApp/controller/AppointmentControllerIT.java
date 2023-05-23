package com.example.CalendarApp.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.CalendarApp.TestConfig;
import com.example.CalendarApp.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentControllerIT extends TestConfig {
    @Test
    void getInterviewsTest() throws Exception {
        Appointment a1 = new Appointment(1, LocalDateTime.now(), null, List.of());
        Appointment a2 = new Appointment(2, LocalDateTime.now(), null, List.of());
        List<Appointment> appointments = List.of(a1, a2);

        appointmentRepository.saveAll(appointments);

        mockMvc.perform(get("/api/appointments")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
