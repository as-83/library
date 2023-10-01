package com.homework.library.controller;

import com.homework.library.entity.Author;
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
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnAllAuthors_whenFindAllAuthors() {
        String url = HOST + port + PATH;
        ResponseEntity<Author[]> responseEntity = this.restTemplate.getForEntity(url, Author[].class);

        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void shouldAddNewAuthor_whenAddAuthor() {
        String url = HOST + port + PATH;
        ResponseEntity<Author[]> responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).length);
        String authorName = "J. Date";
        HttpEntity<Author> request = new HttpEntity<>(new Author(null, authorName));

        Author createdAuthor = restTemplate.postForObject(url, request, Author.class);

        responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        Author[] body = responseEntity.getBody();
        assertEquals(3, Objects.requireNonNull(body).length);
        assertTrue(Arrays.asList(body).contains(createdAuthor));
        restTemplate.delete(url + "/" + createdAuthor.getId());
    }

    @Test
    public void shouldUpdateAuthor_whenUpdateAuthor() {
        String url = HOST + port + PATH;
        ResponseEntity<Author[]> responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        Author author = Objects.requireNonNull(responseEntity.getBody())[0];
        String newAuthorName = "J. Date";
        author.setAuthorName(newAuthorName);
        HttpEntity<Author> request = new HttpEntity<>(author);

        restTemplate.put(url, request);

        responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        Author updatedAuthor = Objects.requireNonNull(responseEntity.getBody())[0];
        assertEquals(author, updatedAuthor);
    }

    @Test
    public void shouldDeleteAuthor_whenDeleteAuthor() {
        String url = HOST + port + PATH;
        String authorName = "J. Date";
        HttpEntity<Author> request = new HttpEntity<>(new Author(null, authorName));
        restTemplate.postForObject(url, request, Void.class);
        ResponseEntity<Author[]> responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        Author authorToDelete = Objects.requireNonNull(responseEntity.getBody())[2];
        assertEquals(3, Objects.requireNonNull(responseEntity.getBody()).length);

        restTemplate.delete(url + "/" + authorToDelete.getId());

        responseEntity = this.restTemplate.getForEntity(url, Author[].class);
        Author[] body = responseEntity.getBody();
        assertEquals(2, Objects.requireNonNull(body).length);
        assertTrue(Arrays.stream(body).noneMatch(author -> Objects.equals(authorToDelete.getId(), author.getId())));
    }
}