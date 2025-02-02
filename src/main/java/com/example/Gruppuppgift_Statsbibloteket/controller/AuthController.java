package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.JWTUtil;
import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import com.example.Gruppuppgift_Statsbibloteket.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final AdminsRepository adminsRepository;

    @Autowired
    public AuthController(UserService userService, JWTUtil jwtUtil, AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService, AdminsRepository adminsRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.adminsRepository = adminsRepository;
    }

    // en endpoint för att registrera nya användare
    @PostMapping("/register")
    public String register(@Valid @RequestBody Users newUser) {
        userService.registerUser(newUser);
        return "User created";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return jwtUtil.generateToken(userDetails.getUsername());
        } catch (AuthenticationException e) {
            return "invalid credentials";
        }
    }
}
