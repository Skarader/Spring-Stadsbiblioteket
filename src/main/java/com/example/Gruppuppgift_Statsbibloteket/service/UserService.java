package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.RegisterUserDTO;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(Long userId, Users updatedUser) {
        Optional<Users> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            user.setEmail(updatedUser.getEmail());
            user.setName(updatedUser.getName());
            user.setLast_name(updatedUser.getLast_name());

            if (updatedUser.getUser_id() == null) {
                user.setUser_id(user.getUser_id());
            }

            if (updatedUser.getLoans() == null) {
                user.setLoans(user.getLoans());
            }

            if (updatedUser.getMember_number() == null) {
                user.setMember_number(user.getMember_number());
            }

            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }

    public Users registerUser(Users newUser/*String first_name, String last_name, String email, String memeber_number, String userName, String rawPassword*/){
        //Users user = new Users();
        //user.setName(newUser.getName());
        //user.setLast_name(newUser.getLast_name());
        //user.setEmail(newUser.getEmail());
        //user.setMember_number(newUser.getMember_number());
        //user.setUsername(newUser.getUsername());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }
}
