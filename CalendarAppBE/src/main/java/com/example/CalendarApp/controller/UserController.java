package com.example.CalendarApp.controller;

import com.example.CalendarApp.domain.model.User;
import com.example.CalendarApp.domain.model.dto.LoginDto;
import com.example.CalendarApp.service.SequenceGeneratorService;
import com.example.CalendarApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/login")
    public ResponseEntity<UserDetails> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.loadUserByUsername(loginDto.getUsername()));
    }
}
