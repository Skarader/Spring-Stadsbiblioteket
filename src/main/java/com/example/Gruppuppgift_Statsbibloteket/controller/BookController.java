package com.example.Gruppuppgift_Statsbibloteket.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Book> getProductById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }
}