package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.User;
import com.example.CalendarApp.domain.model.dto.LoginDto;
import com.example.CalendarApp.service.SequenceGeneratorService;
import com.example.CalendarApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @PostMapping("/register")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto){
        User user = new User();
        user.setId(sequenceGeneratorService.getSequenceNumber(User.SEQUENCE_NAME));
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(user.getPassword()));

        return ResponseEntity.ok(userService.login(user));
    }
}
