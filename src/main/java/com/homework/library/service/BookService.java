package com.homework.library.service;

import com.homework.library.entity.Book;
import com.homework.library.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return bookMapper.findAllBooks();
    }

    public void addBook(Book book) {
        bookMapper.insert(book);
    }
}
