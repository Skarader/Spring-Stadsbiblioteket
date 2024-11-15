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
        Optional<Author> author = authorService.getAuthorById(bookDTO.getAuthorId());

        if (author.isPresent()) {
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setPublicationYear(bookDTO.getPublicationYear());
            book.setAvailable(bookDTO.getAvailable());
            book.setAuthor(author.get());

            Set<BooksGenres> booksGenres = bookDTO.getBookGenres().stream()
                    .map(genreDTO -> {
                        Genres genre = genresService.getGenresById(genreDTO.getGenreId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Genre not found with ID: " + genreDTO.getGenreId()));

                        BooksGenres booksGenre = new BooksGenres();
                        booksGenre.setGenre(genre);
                        booksGenre.setBook(book);
                        return booksGenre;
                    })
                    .collect(Collectors.toSet());

            book.setBooksGenres(booksGenres);

            Book savedBook = bookService.saveBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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