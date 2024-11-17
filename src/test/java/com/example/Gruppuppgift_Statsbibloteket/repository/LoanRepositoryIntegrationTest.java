package com.example.Gruppuppgift_Statsbibloteket.repository;

import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LoanRepositoryIntegrationTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Users testUser;
    private Book testBook;

    @BeforeEach
    void setUp() {
        // Create and save a test user
        testUser = new Users("John", "Doe", "john.doe@example.com", "12345");
        userRepository.save(testUser);

        // Create and save a test author
        Author testAuthor = new Author();
        testAuthor.setFirstName("Test");
        testAuthor.setLastName("Author");
        testAuthor.setBirthDate(LocalDate.of(1980, 1, 1));
        authorRepository.save(testAuthor);

        // Create and save a test book
        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setPublicationYear(2021);
        testBook.setAvailable(true);
        testBook.setAuthor(testAuthor);
        bookRepository.save(testBook);
    }

    @Test
    void testFindByBookIdAndReturnedDateIsNull() {
        // Given
        Loan loan = new Loan();
        loan.setUserId(testUser.getUser_id());
        loan.setBookId(testBook.getBookId());
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(30));
        loanRepository.save(loan);

        // When
        List<Loan> loans = loanRepository.findByBookIdAndReturnedDateIsNull(testBook.getBookId());

        // Then
        assertFalse(loans.isEmpty());
        assertEquals(testBook.getBookId(), loans.get(0).getBookId());
        assertNull(loans.get(0).getReturnedDate());
    }

    @Test
    void testFindByUserId() {
        // Given
        Loan loan = new Loan();
        loan.setUserId(testUser.getUser_id());
        loan.setBookId(testBook.getBookId());
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(30));
        loanRepository.save(loan);

        // When
        List<Loan> loans = loanRepository.findByUserId(testUser.getUser_id());

        // Then
        assertFalse(loans.isEmpty());
        assertEquals(testUser.getUser_id(), loans.get(0).getUserId());
    }

    @Test
    void testFindByBookIdAndUserIdAndReturnedDateIsNull() {
        // Given
        Loan loan = new Loan();
        loan.setUserId(testUser.getUser_id());
        loan.setBookId(testBook.getBookId());
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(30));
        loanRepository.save(loan);

        // When
        Optional<Loan> optionalLoan = loanRepository.findByBookIdAndUserIdAndReturnedDateIsNull(
                testBook.getBookId(),
                testUser.getUser_id()
        );

        // Then
        assertTrue(optionalLoan.isPresent());
        assertEquals(testBook.getBookId(), optionalLoan.get().getBookId());
        assertEquals(testUser.getUser_id(), optionalLoan.get().getUserId());
        assertNull(optionalLoan.get().getReturnedDate());
    }
}
