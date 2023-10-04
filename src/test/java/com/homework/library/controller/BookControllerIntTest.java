package com.homework.library.controller;

import com.homework.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.EXHAUSTIVE)
class BookControllerIntTest {
    @Value(value = "${local.server.port}")
    private int port;
    private String url;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void setup() {
        url = "http://localhost:" + port + "/v1/api/books";
    }

    @Test
    public void shouldReturnAllBooks_whenGetBooks_noParameters() {
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(url, Book[].class);

        assertEquals(3, responseEntity.getBody().length);
    }

    @Test
    public void shouldAddNewBook_whenAddBook() {
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(url, Book[].class);
        assertEquals(3, responseEntity.getBody().length);
        String authorName = "J. Date";
        String title = "Database in Depth";
        String genre = "IT";
        HttpEntity<Book> request = new HttpEntity<>(new Book(null, authorName, title, genre));

        Book createdBook = restTemplate.postForObject(url, request, Book.class);

        responseEntity = restTemplate.getForEntity(url, Book[].class);
        Book[] body = responseEntity.getBody();
        assertEquals(4, body.length);
        assertTrue(Arrays.asList(body).contains(createdBook));
        restTemplate.delete(url + "/" + createdBook.getId());
    }


    @Test
    public void shouldUpdateBook_whenUpdateBook() {
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(url, Book[].class);
        Book book = responseEntity.getBody()[0];
        book.setTitle("New Title");

        restTemplate.put(url, new HttpEntity<>(book));

        responseEntity = restTemplate.getForEntity(url, Book[].class);
        Book updatedBook = responseEntity.getBody()[0];
        assertEquals(book, updatedBook);
    }

    @Test
    public void shouldDeleteBook_whenDeleteBook() {
        String authorName = "J. Date";
        String title = "Database in Depth";
        String genre = "IT";
        HttpEntity<Book> request = new HttpEntity<>(new Book(null, authorName, title, genre));
        Book bookToDelete = restTemplate.postForObject(url, request, Book.class);

        restTemplate.delete(url + "/" + bookToDelete.getId());

        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(url, Book[].class);
        Book[] body = responseEntity.getBody();
        assertEquals(3, body.length);
        assertTrue(Arrays.stream(body).noneMatch(book -> Objects.equals(bookToDelete.getId(), book.getId())));
    }
}