package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.model.Admins;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.AdminsRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminsService {
    private final AdminsRepository adminsRepository;
    private final UserRepository userRepository;
    public AdminsService(AdminsRepository adminsRepository, UserRepository userRepository) {
        this.adminsRepository = adminsRepository;
        this.userRepository = userRepository;
    }

    public List<Admins> getAllAdmins() {
        return adminsRepository.findAll();
    }

    public Users createUser(Users newUser, String username, String password) {
        Optional<Admins> admin = adminsRepository.findByUsernameAndPassword(username, password);
        if (admin.isPresent()) {
            return userRepository.save(newUser);
        }
        else {
           throw new SecurityException("Test");
        }
    }


}
