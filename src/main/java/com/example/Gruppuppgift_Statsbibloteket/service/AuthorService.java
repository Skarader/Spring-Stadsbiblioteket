package com.example.Gruppuppgift_Statsbibloteket.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.Dto.AuthorDTO;
import com.example.Gruppuppgift_Statsbibloteket.exception.ResourceNotFoundException;
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.model.Book;
import com.example.Gruppuppgift_Statsbibloteket.model.BooksGenres;
import com.example.Gruppuppgift_Statsbibloteket.model.Genres;
import com.example.Gruppuppgift_Statsbibloteket.repository.AuthorRepository;
import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenresService genresService;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository,
            GenresService genresService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genresService = genresService;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBirthDate(authorDTO.getBirthDate());
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, AuthorDTO authorDTO) {
        // get the existing author
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));

        // Update fields
        if (authorDTO.getFirstName() != null) {
            existingAuthor.setFirstName(authorDTO.getFirstName());
        }
        if (authorDTO.getLastName() != null) {
            existingAuthor.setLastName(authorDTO.getLastName());
        }
        if (authorDTO.getBirthDate() != null) {
            existingAuthor.setBirthDate(authorDTO.getBirthDate());
        }

        // Update books
        if (authorDTO.getBooks() != null) {
            List<Book> updatedBooks = authorDTO.getBooks().stream().map(bookDTO -> {
                // get or create a book
                Book book = bookDTO.getBookId() != null
                        ? bookRepository.findById(bookDTO.getBookId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Book not found with ID: " + bookDTO.getBookId()))
                        : new Book();

                // Update fields
                if (bookDTO.getTitle() != null) {
                    book.setTitle(bookDTO.getTitle());
                }
                if (bookDTO.getPublicationYear() != 0) {
                    book.setPublicationYear(bookDTO.getPublicationYear());
                }
                if (bookDTO.getAvailable() != null) {
                    book.setAvailable(bookDTO.getAvailable());
                }

                // Update genres
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

            // remove current and add new books
            existingAuthor.getBooks().clear();
            existingAuthor.getBooks().addAll(updatedBooks);
        }

        // Save updated author
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        // get author by id
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + id));

        // check if any of the authors books are loaned out
        boolean loanedBooks = existingAuthor.getBooks()
                .stream().anyMatch(book -> !book.getAvailable());

        if (loanedBooks) {
            throw new ResourceNotFoundException("Cant delete a author with books loaned out!");
        }
        // delete author
        authorRepository.deleteById(id);
    }
}
