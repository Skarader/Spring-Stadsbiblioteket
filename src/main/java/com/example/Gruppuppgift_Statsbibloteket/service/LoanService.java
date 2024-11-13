package com.example.Gruppuppgift_Statsbibloteket.service;

import com.example.Gruppuppgift_Statsbibloteket.repository.LoanRepository;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Gruppuppgift_Statsbibloteket.exception.LoanIsNotAvailableException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan loan) {
        // Check if book is already loaned
        List<Loan> existingLoans = loanRepository.findByBookIdAndReturnedDateIsNull(loan.getBookId());
        if (!existingLoans.isEmpty()) {
            throw new LoanIsNotAvailableException("Book is already loaned!");
        }

        // Set loan date and calculate due date (e.g., 14 days from loan date)
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(loan.getLoanDate().plusDays(30));

        return loanRepository.save(loan);
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan not found"));
    }

    public Loan returnBook(Long id) {
        Loan loan = getLoan(id);
        if (loan.getReturnedDate() != null) {
            throw new LoanIsNotAvailableException("Book is already returned");
        }
        loan.setReturnedDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    public List<Loan> getUserLoans(Long userId) {
        return loanRepository.findByUserId(userId);
    }
}
