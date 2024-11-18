package com.example.Gruppuppgift_Statsbibloteket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private String title;
    private Integer publicationYear;
    private Long authorId; // Use authorId directly
    private Boolean available;
}
