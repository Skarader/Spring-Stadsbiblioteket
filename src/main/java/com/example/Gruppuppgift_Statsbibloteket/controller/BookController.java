package com.example.Gruppuppgift_Statsbibloteket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gruppuppgift_Statsbibloteket.Dto.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.exception.ResourceNotFoundException;
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.BooksGenres;
import com.example.Gruppuppgift_Statsbibloteket.model.Genres;
import com.example.Gruppuppgift_Statsbibloteket.service.AuthorService;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;
import com.example.Gruppuppgift_Statsbibloteket.service.GenresService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenresService genresService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenresService genresService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genresService = genresService;
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

    // GET BOOKS BY AUTHOR
    @GetMapping("/author/{name}")
    public ResponseEntity<List<Book>> getBooksByAuthorName(@PathVariable String name) {
        List<Book> books = bookService.findBooksByAuthorName(name);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

    // CREATE NEW BOOK
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {

        Author author = authorService.getAuthorById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + bookDTO.getAuthorId()));

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setAvailable(bookDTO.getAvailable());
        book.setAuthor(author);

        Set<BooksGenres> booksGenres = bookDTO.getBookGenreIds().stream()
                .map(genreId -> {
                    Genres genre = genresService.getGenresById(genreId)
                            .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + genreId));
                    BooksGenres bookGenre = new BooksGenres();
                    bookGenre.setBook(book);
                    bookGenre.setGenre(genre);
                    return bookGenre;
                })
                .collect(Collectors.toSet());

        book.setBooksGenres(booksGenres);

        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // UPDATE BOOK BY ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {

        Book existingBook = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        existingBook.setAvailable(bookDTO.getAvailable());

        if (bookDTO.getAuthorId() != null) {
            Author author = authorService.getAuthorById(bookDTO.getAuthorId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Author not found with ID: " + bookDTO.getAuthorId()));
            existingBook.setAuthor(author);
        }

        if (bookDTO.getBookGenreIds() != null) {
            Set<BooksGenres> updatedGenres = bookDTO.getBookGenreIds().stream()
                    .map(genreId -> {
                        Genres genre = genresService.getGenresById(genreId)
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Genre not found with ID: " + genreId));
                        BooksGenres bookGenre = new BooksGenres();
                        bookGenre.setBook(existingBook);
                        bookGenre.setGenre(genre);
                        return bookGenre;
                    })
                    .collect(Collectors.toSet());
            existingBook.setBooksGenres(updatedGenres);
        }

        Book updatedBook = bookService.saveBook(existingBook);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // DELETE BOOK BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}