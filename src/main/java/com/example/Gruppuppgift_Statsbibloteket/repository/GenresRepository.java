package com.example.Gruppuppgift_Statsbibloteket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Gruppuppgift_Statsbibloteket.model.Genres;

public interface GenresRepository extends JpaRepository<Genres, Long> {

    @Query("SELECT b FROM Genres b WHERE LOWER(b.name) = LOWER(:name)")
    Optional<Genres> findGenresByname(@Param("name") String name);
}
