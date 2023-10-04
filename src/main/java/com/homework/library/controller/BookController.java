package com.homework.library.controller;

import com.homework.library.entity.Book;
import com.homework.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "100") Integer limit
    ) {
        return bookService.getBooksByAuthorAndGenre(author, genre, page, limit);
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public void updateBook(@Valid @RequestBody Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
