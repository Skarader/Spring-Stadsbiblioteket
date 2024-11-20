package com.example.Gruppuppgift_Statsbibloteket.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;

public class BookControllerUnitTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookById_Success() {

        // Arrange
        Book testBook = new Book();
        testBook.setBookId(1L);
        testBook.setTitle("1984");
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

        // Act
        ResponseEntity<Book> response = bookController.getBookById(1L);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals("1984", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_Fali() {

        // Arrange
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> response = bookController.getBookById(99L);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(99L);
    }

}