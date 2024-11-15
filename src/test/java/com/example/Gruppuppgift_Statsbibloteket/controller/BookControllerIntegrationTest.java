package com.example.Gruppuppgift_Statsbibloteket.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.repository.AuthorRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void getBookById_Success() throws Exception {

        Author author = new Author();
        author.setFirstName("George");
        author.setLastName("Orwell");
        author.setBirthDate(LocalDate.parse("1903-06-25"));
        author = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("1984");
        book.setPublicationYear(1949);
        book.setAvailable(true);
        book.setAuthor(author);
        book = bookRepository.save(book);

        mockMvc.perform(get("/books/{id}", book.getBookId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("1984")))
                .andExpect(jsonPath("$.publicationYear", is(1949)))
                .andExpect(jsonPath("$.available", is(true)));

    }

    @Test
    void getBookById_Fail() throws Exception {

        mockMvc.perform(get("/books/{id}", 99999L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

// mockMvc.perform(get("/books/{id}", 999L)
// .accept(MediaType.APPLICATION_JSON))
// .andExpect(status().isNotFound());

// private String firstName;
// private String lastName;
// private LocalDate birthDate;

// 25 June 1903
