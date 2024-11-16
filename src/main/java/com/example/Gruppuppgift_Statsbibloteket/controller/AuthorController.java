package com.example.Gruppuppgift_Statsbibloteket.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.Gruppuppgift_Statsbibloteket.model.Author;
import com.example.Gruppuppgift_Statsbibloteket.service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
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
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isPresent()) {
            Author existingAuthor = author.get();
            existingAuthor.setFirstName(authorDTO.getFirstName());
            existingAuthor.setLastName(authorDTO.getLastName());
            existingAuthor.setBirthDate(authorDTO.getBirthDate());
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
