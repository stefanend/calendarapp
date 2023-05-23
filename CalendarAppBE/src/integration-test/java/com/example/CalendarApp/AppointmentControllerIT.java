package com.example.CalendarApp;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Interviewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AppointmentControllerIT extends TestConfig {

    private Appointment appointment;
    private List<Interviewer> interviewerList;

    private final String BODY_APPOINTMENT = "{\n" +
            "    \"day\": \"2001-05-10T15:00:00.000Z\",\n" +
            "    \"candidate\": null,\n" +
            "    \"interviewers\": []\n" +
            "}";

    private final String BODY_APPOINTMENT_EXCEPTION ="{\n" +
            "    \"day\": \"2023-05-10T15:00:00.000Z\",\n" +
            "    \"candidate\": null,\n" +
            "    \"interviewers\": [{\n" +
            "        \"firstName\": \"Tatjana\",\n" +
            "        \"lastName\": \"Kunic\",\n" +
            "        \"experienced\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"firstName\": \"Stefan\",\n" +
            "        \"lastName\": \"Marjanovic\",\n" +
            "        \"experienced\": true\n" +
            "    }\n" +
            "    ]\n" +
            "}";

    private final String BODY_INTERVIEWER = "{\n" +
            "    \"firstName\": \"Stefan\",\n" +
            "    \"lastName\": \"Marjanovic\",\n" +
            "    \"experienced\": false\n" +
            "}";

    @BeforeEach
    void setUp() {
        appointment = new Appointment(1, LocalDateTime.now(), null, Collections.emptyList());

        appointmentRepository.save(appointment);
    }

    @Test
    void getAppointmentsByDateRange() throws Exception {
        mockMvc.perform(get("/api/appointments/date")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", "2023-05-08T11:07:39.000Z")
                        .param("endDate", "2023-06-08T11:07:39.000Z")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void insertAppointment() throws Exception {
        mockMvc.perform(post("/api/appointments")
                .contentType(MediaType.APPLICATION_JSON).content(BODY_APPOINTMENT)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void insertAppointmentException() throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_APPOINTMENT_EXCEPTION)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", equalTo(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalTo("CA-4001")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", equalTo("There can't be multiple interviewers with the same expirience per appointment!")));;
    }

    @Test
    void insertInterviewer() throws Exception {
        mockMvc.perform(post("/api/appointments/interviewer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_INTERVIEWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void insertInterviewerException() throws Exception {
        mockMvc.perform(post("/api/appointments/interviewer/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_INTERVIEWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", equalTo(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalTo("CA-4000")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", equalTo("Appointment with the id: 2 does not exist!")));;
    }
}
