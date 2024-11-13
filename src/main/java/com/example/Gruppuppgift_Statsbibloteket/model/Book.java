package com.example.Gruppuppgift_Statsbibloteket.model;

import com.example.Gruppuppgift_Statsbibloteket.repository.BookRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @JoinColumn(name = "BOOK_ID")
    Long id;

    @JoinColumn(name = "TITLE")
    String title;

    @JoinColumn(name = "PUBLICATION_YEAR")
    int publicationYear;

    @JoinColumn(name = "AUTHOR_ID")
    int authorId;

    @JoinColumn(name = "AVAILABLE")
    boolean isAvailable;

}
