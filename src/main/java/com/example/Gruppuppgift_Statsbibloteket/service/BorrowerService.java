package com.example.Gruppuppgift_Statsbibloteket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Borrower;
import com.example.Gruppuppgift_Statsbibloteket.repository.BorrowerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);
    }

    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }
}
