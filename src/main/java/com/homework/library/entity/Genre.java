package com.homework.library.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
    private Long id;
    @NotBlank(message = "Genre is mandatory")
    private String genreTitle;
}
