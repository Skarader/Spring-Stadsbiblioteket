package com.example.Gruppuppgift_Statsbibloteket;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends User {
    private String firstName;
    private String lastName;
    private String email;

    public Admin(int userId, String firstName, String lastName, String email, boolean isAdmin,
            List<Book> borrowedBooks) {
        super(userId, firstName, lastName, email, isAdmin, borrowedBooks);
    }
}
