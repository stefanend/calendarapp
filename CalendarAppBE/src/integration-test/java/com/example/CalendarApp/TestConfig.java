package com.example.CalendarApp;

import com.example.CalendarApp.domain.repository.AppointmentRepository;
import com.example.CalendarApp.domain.repository.InterviewerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("file:src/integration-test/resources/application-test.properties")
@SpringBootTest
public class TestConfig {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    public AppointmentRepository appointmentRepository;

    @Autowired
    public InterviewerRepository interviewerRepository;

    @BeforeAll
    void setUpRestTemplate() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void clean() {
        appointmentRepository.deleteAll();
        interviewerRepository.deleteAll();
    }
}
