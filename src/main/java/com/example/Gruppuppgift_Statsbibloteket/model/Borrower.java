package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;

@Entity
@Table(name = "BORROWERS")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BORROWER_ID")
    private Long borrowerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore // Ignorera i JSON
    @ElementCollection
    @CollectionTable(
            name = "BORROWER_LOAN_IDS",
            joinColumns = @JoinColumn(name = "BORROWER_ID")
    )
    @Column(name = "LOAN_IDS")
    private List<Long> loanIds = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "BORROWER_BOOKS",
            joinColumns = @JoinColumn(name = "BORROWER_ID"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    private Set<Book> books = new HashSet<>();

    // Standardkonstruktor
    public Borrower() {}

    // Ny konstruktor som matchar testernas parametrar
    public Borrower(String name, String email, Set<Book> books) {
        this.name = name;
        this.email = email;
        this.books = books;
    }

    // Getters och setters
    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<Long> loanIds) {
        this.loanIds = loanIds;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
