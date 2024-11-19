package com.example.Gruppuppgift_Statsbibloteket.repository;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AdminGetBorrowedBooks;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Method to check if a book is already loaned (and not yet returned)
    List<Loan> findByBookIdAndReturnedDateIsNull(Long bookId);

    // Method to find loans by user
    List<Loan> findByUserId(Long userId);

    // Method to check if a book is loaned by a specific user
    Optional<Loan> findByBookIdAndUserIdAndReturnedDateIsNull(Long bookId, Long userId);
    //Optional<Users> findByfirstnameandlastname(String firstname, String lastname);

    @Query("SELECT new com.example.Gruppuppgift_Statsbibloteket.Dto.AdminGetBorrowedBooks(l.book, l.user) " +
            "FROM Loan l " +
            "JOIN l.book b " +
            "WHERE b.available = false")
    List<AdminGetBorrowedBooks> findBorrowedBooksWithUser();
}


