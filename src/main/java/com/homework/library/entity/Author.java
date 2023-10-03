package com.homework.library.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
}
