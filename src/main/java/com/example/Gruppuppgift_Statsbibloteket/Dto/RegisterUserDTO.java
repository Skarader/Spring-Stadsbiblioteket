package com.example.Gruppuppgift_Statsbibloteket.Dto;

import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String memberNumber;
    private String username;
    private String password;
    private String role;
}
