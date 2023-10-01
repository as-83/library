package com.homework.library.controller;

import com.homework.library.entity.Genre;
import com.homework.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/genre")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @PostMapping
    public Genre addGenre(@RequestBody Genre genre) {
        return genreService.addGenre(genre);
    }

    @PutMapping
    public void updateGenre(@RequestBody Genre genre) {
        genreService.updateGenre(genre);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
}
