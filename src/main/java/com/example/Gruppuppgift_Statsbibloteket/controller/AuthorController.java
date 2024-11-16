package com.example.Gruppuppgift_Statsbibloteket.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AuthorDTO;
import com.example.Gruppuppgift_Statsbibloteket.exception.ResourceNotFoundException;
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.BooksGenres;
import com.example.Gruppuppgift_Statsbibloteket.model.Genres;
import com.example.Gruppuppgift_Statsbibloteket.service.AuthorService;
import com.example.Gruppuppgift_Statsbibloteket.service.BookService;
import com.example.Gruppuppgift_Statsbibloteket.service.GenresService;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final GenresService genresService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService, GenresService genresService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genresService = genresService;
    }

    // GET ALL AUTHORS
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    // GET AUTHOR BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // CREATE NEW AUTHOR
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDTO authorDTO) {

        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBirthDate(authorDTO.getBirthDate());

        Author savedAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    // UPDATE AUTHOR BY ID
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<Author> existingAuthorOptional = authorService.getAuthorById(id);

        if (existingAuthorOptional.isPresent()) {
            Author existingAuthor = existingAuthorOptional.get();

            if (authorDTO.getFirstName() != null) {
                existingAuthor.setFirstName(authorDTO.getFirstName());
            }
            if (authorDTO.getLastName() != null) {
                existingAuthor.setLastName(authorDTO.getLastName());
            }
            if (authorDTO.getBirthDate() != null) {
                existingAuthor.setBirthDate(authorDTO.getBirthDate());
            }

            if (authorDTO.getBooks() != null) {
                List<Book> updatedBooks = authorDTO.getBooks().stream().map(bookDTO -> {

                    Book book = bookDTO.getBookId() != null
                            ? bookService.getBookById(bookDTO.getBookId())
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "Book not found with ID: " + bookDTO.getBookId()))
                            : new Book();

                    if (bookDTO.getTitle() != null) {
                        book.setTitle(bookDTO.getTitle());
                    }
                    if (bookDTO.getPublicationYear() != 0) {
                        book.setPublicationYear(bookDTO.getPublicationYear());
                    }
                    if (bookDTO.getAvailable() != null) {
                        book.setAvailable(bookDTO.getAvailable());
                    }

                    if (bookDTO.getBookGenreIds() != null) {

                        if (book.getBooksGenres() != null) {
                            book.getBooksGenres().clear();
                        }

                        Set<BooksGenres> updatedGenres = bookDTO.getBookGenreIds().stream()
                                .map(genreId -> {
                                    Genres genre = genresService.getGenresById(genreId)
                                            .orElseThrow(() -> new ResourceNotFoundException(
                                                    "Genre not found with ID: " + genreId));
                                    BooksGenres booksGenre = new BooksGenres();
                                    booksGenre.setBook(book);
                                    booksGenre.setGenre(genre);
                                    return booksGenre;
                                })
                                .collect(Collectors.toSet());
                        book.getBooksGenres().addAll(updatedGenres);
                    }

                    book.setAuthor(existingAuthor);
                    return book;
                }).collect(Collectors.toList());

                existingAuthor.setBooks(updatedBooks);
            }

            Author updatedAuthor = authorService.createAuthor(existingAuthor);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE AUTHOR BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
