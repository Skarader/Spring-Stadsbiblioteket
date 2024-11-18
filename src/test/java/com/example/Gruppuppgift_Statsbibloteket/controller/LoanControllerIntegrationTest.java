package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;

import com.example.Gruppuppgift_Statsbibloteket.model.Loan;

import com.example.Gruppuppgift_Statsbibloteket.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

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

        Loan mockLoan = new Loan();
        mockLoan.setUserId(userId);
        mockLoan.setBookId(bookId);
        mockLoan.setLoanDate(LocalDate.parse(loanDate));
        mockLoan.setDueDate(LocalDate.parse(dueDate));

        // Mock service behavior
        when(loanService.createLoan(any(UserLoanDto.class))).thenReturn(mockLoan);

        // When & Then
        mockMvc.perform(post("/loans")
                .contentType("application/json")
                .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.loanDate").value(loanDate))
                .andExpect(jsonPath("$.dueDate").value(dueDate));

        verify(loanService, times(1)).createLoan(any(UserLoanDto.class));
    }

    @Test
    void createLoan_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        // Given
        when(loanService.createLoan(any(UserLoanDto.class)))
                .thenThrow(new RuntimeException("User not found"));

        // When & Then
        mockMvc.perform(post("/loans")
                .contentType("application/json")
                .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isNotFound());

        verify(loanService, times(1)).createLoan(any(UserLoanDto.class));
    }

    @Test
    void createLoan_ShouldReturnNotFound_WhenBookNotFound() throws Exception {
        // Given
        when(loanService.createLoan(any(UserLoanDto.class)))
                .thenThrow(new RuntimeException("Book not found"));

        // When & Then
        mockMvc.perform(post("/loans")
                .contentType("application/json")
                .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isNotFound());

        verify(loanService, times(1)).createLoan(any(UserLoanDto.class));
    }
}
