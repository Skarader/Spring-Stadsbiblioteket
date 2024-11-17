package com.example.Gruppuppgift_Statsbibloteket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET LIST OF ALL BOOKS
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // GET A BOOK WITH A ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // GET A BOOK BY TITLE
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // GET BOOKS BY AUTHOR
    public List<Book> findBooksByAuthorName(String name) {
        return bookRepository.findByAuthorName(name);
    }

    // SAVE NEW BOOK
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // DELETE BOOK BY ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBorrowedBooks() {
        return this.bookRepository.findByAvailableFalse();
    }
}