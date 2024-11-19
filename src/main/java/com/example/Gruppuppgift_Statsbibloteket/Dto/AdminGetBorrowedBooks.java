package com.example.Gruppuppgift_Statsbibloteket.Dto;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;

public class AdminGetBorrowedBooks {
    private Book book;
    private Users user;

    public AdminGetBorrowedBooks(Book book, Users user) {
        this.book = book;
        this.user = user;
    }

    public AdminGetBorrowedBooks() {}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}


