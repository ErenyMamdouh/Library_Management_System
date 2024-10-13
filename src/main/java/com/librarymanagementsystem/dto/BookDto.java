package com.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long bookId;
    private String title;
    private String author;
    private String isbn;

}
