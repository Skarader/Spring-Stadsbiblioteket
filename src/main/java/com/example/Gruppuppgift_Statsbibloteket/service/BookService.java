package com.example.Gruppuppgift_Statsbibloteket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;

@Service
public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}