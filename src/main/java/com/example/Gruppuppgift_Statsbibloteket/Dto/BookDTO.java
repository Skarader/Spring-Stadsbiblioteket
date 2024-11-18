package com.example.Gruppuppgift_Statsbibloteket.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    private Long bookId;
    private String title;
    private Integer publicationYear;
    private Long authorId;
    private Boolean available;
    private List<Long> bookGenreIds;
}
