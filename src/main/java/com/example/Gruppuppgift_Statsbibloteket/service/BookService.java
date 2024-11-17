package com.example.Gruppuppgift_Statsbibloteket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.BookDTO;
import com.example.Gruppuppgift_Statsbibloteket.exception.ResourceNotFoundException;
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.BooksGenres;
import com.example.Gruppuppgift_Statsbibloteket.model.Genres;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenresService genresService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, GenresService genresService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genresService = genresService;
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
    public Book createBook(BookDTO bookDTO) {
        // get author with id
        Author author = authorService.getAuthorById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + bookDTO.getAuthorId()));

        // create and insert info into new book
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setAvailable(bookDTO.getAvailable());
        book.setAuthor(author);

        // set genres
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

        // save book
        return bookRepository.save(book);
    }

    // UPDATE BOOK BY ID
    public Book updateBook(Long id, BookDTO bookDTO) {
        // get book with id
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        // Update fields
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        existingBook.setAvailable(bookDTO.getAvailable());

        // Update author
        if (bookDTO.getAuthorId() != null) {
            Author author = authorService.getAuthorById(bookDTO.getAuthorId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Author not found with ID: " + bookDTO.getAuthorId()));
            existingBook.setAuthor(author);
        }

        // Update genres
        if (bookDTO.getBookGenreIds() != null) {
            // remove existing genres
            if (existingBook.getBooksGenres() != null) {
                existingBook.getBooksGenres().clear();
            }

            // set the new genres to the book
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
            existingBook.getBooksGenres().addAll(updatedGenres);
        }

        // Save book
        return bookRepository.save(existingBook);
    }

    // DELETE BOOK BY ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBorrowedBooks() {
        return this.bookRepository.findByAvailableFalse();
    }
}