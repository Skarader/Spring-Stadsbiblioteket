package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminAddUserDTO;
import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.service.AdminsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminsController {
    private final AdminsService adminsService;
    public AdminsController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @GetMapping("/admins")
    public List<Admins> getAllAdmins() {
        return adminsService.getAllAdmins();
    }

    @PostMapping("/admins/create/user")
    public Users createUser(@RequestBody AdminAddUserDTO request) {
        return adminsService.createUser(
                request.getNewUser(),
                request.getUsername(),
                request.getPassword()
        );
    }
}
