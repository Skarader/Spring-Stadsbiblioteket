package com.example.Gruppuppgift_Statsbibloteket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Gruppuppgift_Statsbibloteket.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
