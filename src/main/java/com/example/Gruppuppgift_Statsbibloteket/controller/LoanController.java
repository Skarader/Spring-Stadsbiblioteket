package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Create loan method
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody UserLoanDto userLoanDto) {
        Loan loan = loanService.createLoan(userLoanDto);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }
}
