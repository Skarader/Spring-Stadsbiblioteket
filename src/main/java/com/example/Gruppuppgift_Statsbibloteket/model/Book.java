package com.example.Gruppuppgift_Statsbibloteket;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvalible;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean getIsAvalible() {
        return isAvalible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setIsAvalible(boolean isAvalible) {
        this.isAvalible = isAvalible;
    }
}
