package com.homework.library.service;

import com.homework.library.entity.Genre;
import com.homework.library.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreMapper genreMapper;

    public List<Genre> getAllGenres() {
        return genreMapper.findAllGenres();
    }

    public Genre addGenre(Genre genre) {
        genreMapper.addGenre(genre);
        log.info("Added new genre: {}", genre);
        return genre;
    }

    public void updateGenre(Genre genre) {
        genreMapper.updateGenre(genre);
        log.info("Updated genre: {}", genre);
    }

    public void deleteGenre(Long id) {
        genreMapper.deleteGenre(id);
        log.info("Deleted genre with id: {}", id);
    }

}
