package com.example.Gruppuppgift_Statsbibloteket.model;

import com.fasterxml.jackson.annotation.JsonFormat;  // Import the annotation
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LOANS")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Loan_id")
    private Long id;

    @Column(name = "Book_id", nullable = false)
    private Long bookId;

    @Column(name = "User_id", nullable = false)
    private Long userId;

    @Column(name = "Loan_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  // Added JsonFormat here
    private LocalDate loanDate;

    @Column(name = "Due_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")  // Added JsonFormat here
    private LocalDate dueDate;

    @Column(name = "Returned_date")
    private LocalDate returnedDate;

    // För att endast hämta boken vid behov
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Book_id", insertable = false, updatable = false)
    private Book book;

    // För att endast hämta användaren vid behov
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", insertable = false, updatable = false)
    private Users user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
