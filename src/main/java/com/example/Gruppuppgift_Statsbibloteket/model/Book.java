package com.example.Gruppuppgift_Statsbibloteket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvalible;

    public Book(String title, String author, String ISBN, boolean isAvalible) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvalible = isAvalible;
    }

}
