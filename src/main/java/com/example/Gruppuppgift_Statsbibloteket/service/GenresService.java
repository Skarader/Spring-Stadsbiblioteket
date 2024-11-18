package com.example.Gruppuppgift_Statsbibloteket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gruppuppgift_Statsbibloteket.model.Genres;
import com.example.Gruppuppgift_Statsbibloteket.repository.GenresRepository;

@Service
public class GenresService {

    private final GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public Optional<Genres> getGenresById(Long id) {
        return genresRepository.findById(id);
    }

    public Optional<Genres> getGenresByTitle(String name) {
        return genresRepository.findGenresByname(name);
    }
}
