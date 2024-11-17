package com.example.Gruppuppgift_Statsbibloteket.Dto;

import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAddUserDTO {
        private String username;
        private String password;
        private Users newUser;
}
