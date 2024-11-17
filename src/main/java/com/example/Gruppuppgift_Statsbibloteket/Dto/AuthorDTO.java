package com.example.Gruppuppgift_Statsbibloteket.Dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<BookDTO> books;

}
