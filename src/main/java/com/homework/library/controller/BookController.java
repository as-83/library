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
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("books")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }
}
