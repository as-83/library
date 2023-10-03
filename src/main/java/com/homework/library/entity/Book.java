package com.homework.library.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    @NotBlank(message = "Author is mandatory")
    private String author;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Genre is mandatory")
    private String genre;
}
