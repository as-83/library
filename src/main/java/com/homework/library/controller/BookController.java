package com.homework.library.controller;

import com.homework.library.entity.Book;
import com.homework.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api")
public class BookController {
    private final BookService bookService;

    @GetMapping("books")
    public List<Book> getAllBooks(@RequestParam(required = false) String author, @RequestParam(required = false) String genre) {
        return bookService.getBooksByAuthorAndGenre(author, genre);
    }

    @PostMapping("books")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("books")
    public void updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
