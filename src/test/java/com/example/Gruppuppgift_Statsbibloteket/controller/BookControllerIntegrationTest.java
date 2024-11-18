package com.example.Gruppuppgift_Statsbibloteket.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;

import com.example.Gruppuppgift_Statsbibloteket.service.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBookById_Success() throws Exception {
        // create author
        Author author = new Author();
        author.setFirstName("George");
        author.setLastName("Orwell");
        author.setBirthDate(LocalDate.parse("1903-06-25"));

        // create books for author
        Book book = new Book();
        book.setTitle("1984");
        book.setPublicationYear(1949);
        book.setAvailable(true);
        book.setAuthor(author);

        // mock service and a get request for created book, expect certain results
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("1984")))
                .andExpect(jsonPath("$.publicationYear", is(1949)))
                .andExpect(jsonPath("$.available", is(true)));

        verify(bookService).getBookById(1L);
    }

    @Test
    void getBookById_Fail() throws Exception {
        when(bookService.getBookById(99999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/{id}", 99999L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookService).getBookById(99999L);
    }

}

// mockMvc.perform(get("/books/{id}", 999L)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isNotFound());

// private String firstName;
// private String lastName;
// private LocalDate birthDate;

// 25 June 1903
