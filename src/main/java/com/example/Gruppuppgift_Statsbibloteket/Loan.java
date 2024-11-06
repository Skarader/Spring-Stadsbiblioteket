package com.example.Gruppuppgift_Statsbibloteket;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan {
    private int loanId;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Loan(int loanId, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
