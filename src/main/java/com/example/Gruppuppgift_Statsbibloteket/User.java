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
    private boolean isAdmin = false;
    private int borrowerId;
    private List<Book> borrowedBooks = new ArrayList();

    public User(int userId, String firstName, String lastName, String email, boolean isAdmin,
            List<Book> borrowedBooks) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.borrowedBooks = borrowedBooks;

    }
}
