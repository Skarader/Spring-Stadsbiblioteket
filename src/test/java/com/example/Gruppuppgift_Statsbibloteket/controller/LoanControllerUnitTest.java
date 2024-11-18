package com.example.Gruppuppgift_Statsbibloteket.controller;

import com.example.Gruppuppgift_Statsbibloteket.Dto.UserLoanDto;
import com.example.Gruppuppgift_Statsbibloteket.model.Loan;
import com.example.Gruppuppgift_Statsbibloteket.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LoanControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
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
        mockLoan.setLoanDate(java.time.LocalDate.parse(loanDate));
        mockLoan.setDueDate(java.time.LocalDate.parse(dueDate));

        when(loanService.createLoan(any(UserLoanDto.class))).thenReturn(mockLoan);

        // When & Then
        mockMvc.perform(post("/loans")
                        .contentType("application/json")
                        .content("{\"userId\":1, \"bookId\":1, \"loanDate\":\"2024-11-17\", \"dueDate\":\"2024-12-17\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.bookId").value(bookId))
                .andExpect(jsonPath("$.loanDate").value("2024-11-17")) // The expected format here is the string
                .andExpect(jsonPath("$.dueDate").value("2024-12-17"));

        verify(loanService, times(1)).createLoan(any(UserLoanDto.class));
    }
}
