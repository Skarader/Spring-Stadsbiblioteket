package com.example.Gruppuppgift_Statsbibloteket;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isAdmin = false; // try difference between adding a bool vs useing adminclass.
    private int borrowerId;
    private List<Book> borrowedBooks;

    public User(int userId, String firstName, String lastName, String email, boolean isAdmin) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.borrowedBooks = new ArrayList<>();
    }
}
