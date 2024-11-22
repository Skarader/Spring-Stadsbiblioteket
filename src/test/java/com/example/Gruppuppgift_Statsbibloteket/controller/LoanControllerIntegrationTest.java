package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper; // Add ObjectMapper

    @BeforeEach
    void setUp() {
        // Create and save a test user
        Users user = new Users("Test", "User", "test.user@example.com", "password");
        userRepository.save(user);

        // Create and save a test author
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setBirthDate(java.time.LocalDate.of(1975, 10, 10));
        authorRepository.save(author);

        // Create and save a test book associated with the author
        Book book = new Book();
        book.setTitle("Test Book");
        book.setPublicationYear(2022);
        book.setAvailable(true);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    @Rollback
    void createLoan_ShouldReturnCreatedLoan() throws Exception {
        Long userId = userRepository.findAll().get(0).getUser_id(); // Get the first user from DB
        Long bookId = bookRepository.findAll().get(0).getBookId(); // Get the first book from DB

        // Create loan data
        String loanDate = "2024-11-17";
        String dueDate = "2024-12-17";

        // Create a UserLoanDto
        UserLoanDto userLoanDto = new UserLoanDto(userId, bookId, loanDate, dueDate);

        // Serialize the UserLoanDto into a JSON string using ObjectMapper
        String jsonRequest = objectMapper.writeValueAsString(userLoanDto);

        // Perform the HTTP request to create the loan
        mockMvc.perform(post("/loans")
                        .contentType("application/json")
                        .content(jsonRequest)) // Send the serialized DTO as the request body
                .andExpect(status().isCreated()) // Expect status code 201 (Created)
                .andExpect(jsonPath("$.userId").value(userId)) // Validate userId in response
                .andExpect(jsonPath("$.bookId").value(bookId)) // Validate bookId in response
                .andExpect(jsonPath("$.loanDate").value(loanDate)) // Validate loanDate in response
                .andExpect(jsonPath("$.dueDate").value(dueDate)); // Validate dueDate in response
    }
}
