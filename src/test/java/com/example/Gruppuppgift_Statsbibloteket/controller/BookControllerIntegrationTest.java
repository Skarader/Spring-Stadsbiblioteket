package com.example.Gruppuppgift_Statsbibloteket.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBookById_Success() throws Exception {
        mockMvc.perform(get("/books/{id}", 6)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(6))
                .andExpect(jsonPath("$.title").value("The Shining"))
                .andExpect(jsonPath("$.publicationYear").value(1977))
                .andExpect(jsonPath("$.booksGenres[0].name").value("Horror"))
                .andExpect(jsonPath("$.booksGenres[0].genreId").value(2));

    }

    @Test
    void getBookById_Fail() throws Exception {
        mockMvc.perform(get("/books/{id}", 9999)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
