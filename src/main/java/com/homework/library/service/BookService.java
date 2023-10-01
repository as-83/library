package com.homework.library.service;

import com.homework.library.entity.Author;
import com.homework.library.entity.Book;
import com.homework.library.entity.Genre;
import com.homework.library.mapper.AuthorMapper;
import com.homework.library.mapper.BookMapper;
import com.homework.library.mapper.GenreMapper;
import exception.ParamNotExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Book addBook(Book book) {
        Long authorId = getAuthorIdOrCreateNewAuthor(book);

        Long genreId = getGenreIdOrCreateNew(book);
        bookMapper.insert(book, authorId, genreId);
        log.info("Added new book: {}", book);
        return book;
    }

    public void updateBook(Book book) {
        Long authorId = getAuthorIdOrCreateNewAuthor(book);

        Long genreId = getGenreIdOrCreateNew(book);
        bookMapper.updateBook(book, authorId, genreId);
    }

    public void deleteBook(Long id) {
        bookMapper.deleteBook(id);
    }

    public List<Book> getBooksByAuthorAndGenre(String authorName, String genreTitle) {
        log.info("Getting books By author: {} and Genre: {}", authorName, genreTitle);
        if (isBlank(authorName) && isBlank(genreTitle)) {
            return bookMapper.findAllBooks();
        }
        if (isBlank(authorName)) {
            return getBooksByGenre(genreTitle);
        }

        if (isBlank(genreTitle)) {
            return getBooksByAuthor(authorName);
        }

        Long authorId = getAuthorIdOrThrow(authorName);
        Long genreId = getGenreIdThrow(genreTitle);
        return bookMapper.getBooksByAuthorAndGenre(authorId, genreId);
    }

    private List<Book> getBooksByGenre(String genreTitle) {
        Long genreId = getGenreIdThrow(genreTitle);
        return bookMapper.getBooksByGenreId(genreId);
    }

    private List<Book> getBooksByAuthor(String authorName) {
        Long authorId = getAuthorIdOrThrow(authorName);
        return bookMapper.getBooksByAuthorName(authorId);
    }

    private Long getGenreIdThrow(String genreTitle) {
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

    private Long getGenreIdOrCreateNew(Book book) {
        Long genreId = genreMapper.getGenreIdByTitle(book.getGenre());
        if (isNull(genreId)) {
            genreMapper.addGenre(new Genre(null, book.getGenre()));
            genreId = genreMapper.getGenreIdByTitle(book.getGenre());
        }
        return genreId;
    }

    private Long getAuthorIdOrCreateNewAuthor(Book book) {
        Long authorId = authorMapper.getAuthorIdByName(book.getAuthor());
        if (isNull(authorId)) {
            authorMapper.addAuthor(new Author(null, book.getAuthor()));
            authorId = authorMapper.getAuthorIdByName(book.getAuthor());
        }
        return authorId;
    }
}
