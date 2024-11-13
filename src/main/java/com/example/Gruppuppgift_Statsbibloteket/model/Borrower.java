package com.example.Gruppuppgift_Statsbibloteket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "borrowers")
public class Borrower {

    @ElementCollection
    private List<Long> loanIds = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowerId;

    private String name;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "borrower_books",
            joinColumns = @JoinColumn(name = "borrower_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    public Borrower() {
    }

    public Borrower(String name, String email, Set<Book> books) {
        this.name = name;
        this.email = email;
        this.books = books;
    }

    public List<Long> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<Long> loanIds) {
        this.loanIds = loanIds;
    }

    public void addLoanId(Long loanId) {
        loanIds.add(loanId);
    }
}
