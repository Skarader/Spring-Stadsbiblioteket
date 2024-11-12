package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

}