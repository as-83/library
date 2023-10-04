package com.homework.library.service;

import com.homework.library.entity.Author;
import com.homework.library.entity.Book;
import com.homework.library.entity.Genre;
import com.homework.library.mapper.AuthorMapper;
import com.homework.library.mapper.BookMapper;
import com.homework.library.mapper.GenreMapper;
import com.homework.library.exception.ParamNotExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    @Transactional
    public Book addBook(Book book) {
        Long authorId = getAuthorIdOrCreateNewAuthor(book);

        Long genreId = getGenreIdOrCreateNewGenre(book);
        bookMapper.insert(book, authorId, genreId);
        log.info("Added new book: {}", book);
        return book;
    }

    @Transactional
    public void updateBook(Book book) {
        Long authorId = getAuthorIdOrCreateNewAuthor(book);
        Long genreId = getGenreIdOrCreateNewGenre(book);
        bookMapper.updateBook(book, authorId, genreId);
        log.info("Updated book: {}", book);
    }

    public void deleteBook(Long id) {
        bookMapper.deleteBook(id);
        log.info("Deleted book with id: {}", id);
    }


    public List<Book> getBooksByAuthorAndGenre(String authorName, String genreTitle, Integer page, Integer limit) {
        log.info("Getting books By author: {} and Genre: {}", authorName, genreTitle);
        Long authorId = null;
        Long genreId = null;
        if (!isBlank(authorName)) {
            authorId = getAuthorIdOrThrow(authorName);
        }

        if (!isBlank(genreTitle)) {
            genreId = getGenreIdOrThrow(genreTitle);
        }
        return bookMapper.getBooksByAuthorAndGenreDynamic(authorId, genreId, page, limit);
    }

    private Long getGenreIdOrThrow(String genreTitle) {
        Long genreId = genreMapper.getGenreIdByTitle(genreTitle);
        if (isNull(genreId)) {
            throw new ParamNotExistsException("Genre with this title doesnt exist: " + genreTitle);
        }
        return genreId;
    }

    private Long getAuthorIdOrThrow(String authorName) {
        Long authorId = authorMapper.getAuthorIdByName(authorName);
        if (isNull(authorId)) {
            throw new ParamNotExistsException("Author with this name doesnt exist: " + authorName);
        }
        return authorId;
    }

    private Long getGenreIdOrCreateNewGenre(Book book) {
        Long genreId = genreMapper.getGenreIdByTitle(book.getGenre());
        if (isNull(genreId)) {
            Genre genre = new Genre(null, book.getGenre());
            genreMapper.addGenre(genre);
            genreId = genre.getId();
        }
        return genreId;
    }

    private Long getAuthorIdOrCreateNewAuthor(Book book) {
        Long authorId = authorMapper.getAuthorIdByName(book.getAuthor());
        if (isNull(authorId)) {
            Author author = new Author(null, book.getAuthor());
            authorMapper.addAuthor(author);
            authorId = author.getId();
        }
        return authorId;
    }
}
