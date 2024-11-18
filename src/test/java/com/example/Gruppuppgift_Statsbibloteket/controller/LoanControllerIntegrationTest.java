package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.model.Users;
import com.example.Gruppuppgift_Statsbibloteket.service.LoanService;
import com.example.Gruppuppgift_Statsbibloteket.repository.UserRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLoan_ShouldReturnCreatedLoan() throws Exception {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        String loanDate = "2024-11-17";
        String dueDate = "2024-12-17";

        // Corrected constructor for UserLoanDto with all four parameters
        UserLoanDto userLoanDto = new UserLoanDto(userId, bookId, loanDate, dueDate);

        // Mock User and Book
        Users mockUser = new Users("John", "Doe", "john.doe@example.com", "12345");
        mockUser.setUser_id(userId);
        Book mockBook = new Book();
        mockBook.setBookId(bookId);

        Loan mockLoan = new Loan();
        mockLoan.setUserId(userId);
        mockLoan.setBookId(bookId);
        mockLoan.setLoanDate(java.time.LocalDate.parse(loanDate));
        mockLoan.setDueDate(java.time.LocalDate.parse(dueDate));

        // Mocking repository behavior
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(mockBook));

        // Mocking service behavior
        when(loanService.createLoan(any(UserLoanDto.class)))
                .thenReturn(mockLoan);

        // When & Then
        mockMvc.perform(post("/loans")  // Path fixed to match the controller mapping
                        .contentType("application/json")
                        .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isCreated())  // Expect status CREATED (201)
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.loanDate").value("2024-11-17"))
                .andExpect(jsonPath("$.dueDate").value("2024-12-17"));

        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(loanService, times(1)).createLoan(any(UserLoanDto.class));
    }

    @Test
    void createLoan_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        String loanDate = "2024-11-17";
        String dueDate = "2024-12-17";

        UserLoanDto userLoanDto = new UserLoanDto(userId, bookId, loanDate, dueDate);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // When & Then
        mockMvc.perform(post("/loans")
                        .contentType("application/json")
                        .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isNotFound());  // Expect status NOT FOUND (404)

        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, never()).findById(any());
        verify(loanService, never()).createLoan(any());
    }

    @Test
    void createLoan_ShouldReturnNotFound_WhenBookNotFound() throws Exception {
        // Given
        Long userId = 1L;
        Long bookId = 1L;
        String loanDate = "2024-11-17";
        String dueDate = "2024-12-17";

        UserLoanDto userLoanDto = new UserLoanDto(userId, bookId, loanDate, dueDate);

        Users mockUser = new Users("John", "Doe", "john.doe@example.com", "12345");
        mockUser.setUser_id(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.empty());

        // When & Then
        mockMvc.perform(post("/loans")
                        .contentType("application/json")
                        .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isNotFound());  // Expect status NOT FOUND (404)

        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(loanService, never()).createLoan(any(UserLoanDto.class));
    }
}
