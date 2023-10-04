package com.homework.library.controller;

import com.homework.library.entity.Author;
import com.homework.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public Author addAuthor(@Valid @RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping
    public void updateAuthor(@Valid @RequestBody Author author) {
        authorService.updateAuthor(author);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
