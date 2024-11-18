package com.example.Gruppuppgift_Statsbibloteket.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAddBookDTO {
    private String username;
    private String password;
    private BookDTO newBook;
}
