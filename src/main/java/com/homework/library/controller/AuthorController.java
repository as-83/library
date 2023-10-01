package com.homework.library.controller;

import com.homework.library.entity.Author;
import com.homework.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping
    public void updateAuthor(@RequestBody Author author) {
        authorService.updateAuthor(author);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}