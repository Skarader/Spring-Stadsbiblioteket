package com.example.Gruppuppgift_Statsbibloteket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.service.AuthorService;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // GET ALL BOOKS
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // GET BOOKS BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET BOOK BY TITLE
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        Optional<Book> book = bookService.getBookByTitle(title);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // CREATE NEW BOOK
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {
        Optional<Author> author = authorService.getAuthorById(bookDTO.getAuthorId());

        if (author.isPresent()) {
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setPublicationYear(bookDTO.getPublicationYear());
            book.setAvailable(bookDTO.getAvailable());
            book.setAuthor(author.get());

            Book savedBook = bookService.saveBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something went wrhong", HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE BOOK BY ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setPublicationYear(bookDetails.getPublicationYear());
            existingBook.setAvailable(bookDetails.getAvailable());
            existingBook.setAuthor(bookDetails.getAuthor());
            Book updatedBook = bookService.saveBook(existingBook);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE BOOK BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}