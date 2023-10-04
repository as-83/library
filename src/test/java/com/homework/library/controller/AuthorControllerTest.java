package com.homework.library.controller;

import com.homework.library.entity.Author;
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
class AuthorControllerTest {
    private static final String HOST = "http://localhost:";
    private static final String PATH = "/v1/api/author";
    private String url;
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        url = HOST + port + PATH;
    }


    @Test
    public void shouldReturnAllAuthors_whenFindAllAuthors() {
        ResponseEntity<Author[]> responseEntity = restTemplate.getForEntity(url, Author[].class);

        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void shouldAddNewAuthor_whenAddAuthor() {
        ResponseEntity<Author[]> responseEntity = restTemplate.getForEntity(url, Author[].class);
        assertEquals(2, responseEntity.getBody().length);
        String authorName = "М.Ю. Лермонтов";
        HttpEntity<Author> request = new HttpEntity<>(new Author(null, authorName));

        Author createdAuthor = restTemplate.postForObject(url, request, Author.class);

        responseEntity = restTemplate.getForEntity(url, Author[].class);
        Author[] body = responseEntity.getBody();
        assertEquals(3, body.length);
        assertTrue(Arrays.asList(body).contains(createdAuthor));
        restTemplate.delete(url + "/" + createdAuthor.getId());
    }

    @Test
    public void shouldUpdateAuthor_whenUpdateAuthor() {
        ResponseEntity<Author[]> responseEntity = restTemplate.getForEntity(url, Author[].class);
        Author author = responseEntity.getBody()[0];
        String newAuthorName = "А.С. Пушкин";
        author.setName(newAuthorName);
        HttpEntity<Author> request = new HttpEntity<>(author);

        restTemplate.put(url, request);

        responseEntity = restTemplate.getForEntity(url, Author[].class);
        assertTrue(Arrays.asList(responseEntity.getBody()).contains(author));
    }

    @Test
    public void shouldDeleteAuthor_whenDeleteAuthor() {
        String authorName = "J. Date";
        HttpEntity<Author> request = new HttpEntity<>(new Author(null, authorName));
        restTemplate.postForObject(url, request, Void.class);
        ResponseEntity<Author[]> responseEntity = restTemplate.getForEntity(url, Author[].class);
        Author authorToDelete = responseEntity.getBody()[2];
        assertEquals(3, responseEntity.getBody().length);

        restTemplate.delete(url + "/" + authorToDelete.getId());

        responseEntity = restTemplate.getForEntity(url, Author[].class);
        Author[] body = responseEntity.getBody();
        assertEquals(2, body.length);
        assertTrue(Arrays.stream(body).noneMatch(author -> Objects.equals(authorToDelete.getId(), author.getId())));
    }
}