package com.example.Gruppuppgift_Statsbibloteket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends User {

    public Admin(int userId, String firstName, String lastName, String email, boolean isAdmin) {
        super(userId, firstName, lastName, email, isAdmin);
    }
}
