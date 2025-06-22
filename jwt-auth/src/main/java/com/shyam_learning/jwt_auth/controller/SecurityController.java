package com.shyam_learning.jwt_auth.controller;


import com.shyam_learning.jwt_auth.dto.UserDto;
import com.shyam_learning.jwt_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
