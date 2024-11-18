package com.example.Gruppuppgift_Statsbibloteket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Gruppuppgift_Statsbibloteket.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)")
    Optional<Book> findByTitle(@Param("title") String title);

}