package com.example.CalendarApp;

import com.example.CalendarApp.domain.model.Appointment;
import com.example.CalendarApp.domain.model.Interviewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AppointmentControllerIT extends TestConfig {

    private Appointment appointment;
    private List<Interviewer> interviewerList;

    private final String BODY_APPOINTMENT = """
            {
                "day": "2001-05-10T15:00:00.000Z",
                "candidate": null,
                "interviewers": []
            }""";

    private final String BODY_APPOINTMENT_EXCEPTION = """
            {
                "day": "2023-05-10T15:00:00.000Z",
                "candidate": null,
                "interviewers": [{
                    "firstName": "Tatjana",
                    "lastName": "Kunic",
                    "experienced": true
                },
                {
                    "firstName": "Stefan",
                    "lastName": "Marjanovic",
                    "experienced": true
                }
                ]
            }""";

    private final String BODY_INTERVIEWER = """
            {
                "firstName": "Stefan",
                "lastName": "Marjanovic",
                "experienced": false
            }""";

    private final String BODY_REQUEST_CANDIDATE = """
            {
                "firstName": "Stefan",
                "lastName": "Marjanovic"
            }""";

    @BeforeEach
    void setUp() {
        Appointment a1 = new Appointment(1, LocalDateTime.now(), null, List.of());
        Appointment a2 = new Appointment(2, LocalDateTime.now(), null, List.of());
        List<Appointment> appointments = List.of(a1, a2);

        appointmentRepository.saveAll(appointments);
    }

    @Test
    void getInterviewsTest() throws Exception {

        mockMvc.perform(get("/api/appointments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
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
                        .jsonPath("$.message", equalTo("There can't be multiple interviewers with the same experience per appointment!")));;
    }

    @Test
    void insertCandidateTest() throws Exception {

        mockMvc.perform(post("/api/appointments/candidate/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON).content(BODY_REQUEST_CANDIDATE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.candidate.firstName").value("Stefan"))
                .andExpect(jsonPath("$.candidate.lastName").value("Marjanovic"));
    }

    @Test
    void insertCandidateWhenAppointmentDoesNotExistTest() throws Exception {
        mockMvc.perform(post("/api/appointments/candidate/{id}", "101")
                        .contentType(MediaType.APPLICATION_JSON).content(BODY_REQUEST_CANDIDATE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpCode").value(404))
                .andExpect(jsonPath("$.message").value("Appointment with the id: 101 does not exist!"));
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
        mockMvc.perform(post("/api/appointments/interviewer/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_INTERVIEWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", equalTo(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", equalTo("CA-4000")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message", equalTo("Appointment with the id: 3 does not exist!")));;
    }
    @Test
    void deleteAppointmentTest() throws Exception {
        mockMvc.perform(delete("/api/appointments/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment has been deleted!"));
    }

    @Test
    void deleteAppointmentWhenAppointmentDoesNotExistTest() throws Exception {
        mockMvc.perform(delete("/api/appointments/{id}", "101"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.httpCode").value(404))
                .andExpect(jsonPath("$.message").value("Appointment with the id: 101 does not exist!"));
    }
}
