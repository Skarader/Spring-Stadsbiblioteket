package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminGetBorrowedBooks;
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
import java.util.List;
import java.util.Optional;

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
        book.setAvailable(false);

        Loan loan = new Loan();
        loan.setUserId(user.getUser_id());
        loan.setBookId(book.getBookId());
        loan.setLoanDate(loanDate);
        loan.setDueDate(dueDate);

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> returnBook(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {

            Long bookId = loan.get().getBookId();
            Optional<Book> book = bookRepository.findById(bookId);
            if (book.isPresent()) {
                book.get().setAvailable(true);
            }

            loanRepository.deleteById(loanId);

        }
        return loan;
    }

    public List<AdminGetBorrowedBooks> getBorrowedBooksWithUsers() {
        return loanRepository.findBorrowedBooksWithUser();
    }
}
