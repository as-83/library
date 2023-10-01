package com.homework.library.service;

import com.homework.library.entity.Author;
import com.homework.library.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorMapper authorMapper;

    public List<Author> getAllAuthors() {
        return authorMapper.findAllAuthors();
    }

    public Author addAuthor(Author author) {
        authorMapper.addAuthor(author);
        log.info("Added new author: {}", author);
        return author;
    }

    public void updateAuthor(Author author) {
        authorMapper.updateAuthor(author);
        log.info("Updated author: {}", author);
    }

    public void deleteAuthor(Long id) {
        authorMapper.deleteAuthor(id);
        log.info("Deleted author with id: {}", id);
    }

}
