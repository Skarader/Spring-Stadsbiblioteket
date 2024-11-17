package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.LoanRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    // Method to create a loan
    public Loan createLoan(UserLoanDto userLoanDto) {
        Users user = userRepository.findById(userLoanDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(userLoanDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        LocalDate loanDate = LocalDate.parse(userLoanDto.getLoanDate());
        LocalDate dueDate = LocalDate.parse(userLoanDto.getDueDate());

        Loan loan = new Loan();
        loan.setUserId(user.getUser_id());
        loan.setBookId(book.getBookId());
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);

        return loanRepository.save(loan);
    }
}
