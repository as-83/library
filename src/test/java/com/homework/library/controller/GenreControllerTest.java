package com.homework.library.controller;

import com.homework.library.entity.Genre;
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
class GenreControllerTest {
    private static final String HOST = "http://localhost:";
    private static final String PATH = "/v1/api/genre";
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
    public void shouldReturnAllGenres_whenFindAllGenres() {
        ResponseEntity<Genre[]> responseEntity = restTemplate.getForEntity(url, Genre[].class);

        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void shouldAddNewGenre_whenAddGenre() {
        ResponseEntity<Genre[]> responseEntity = restTemplate.getForEntity(url, Genre[].class);
        assertEquals(2, responseEntity.getBody().length);
        String genreTitle = "Story";
        HttpEntity<Genre> request = new HttpEntity<>(new Genre(null, genreTitle));

        Genre createdGenre = restTemplate.postForObject(url, request, Genre.class);

        responseEntity = restTemplate.getForEntity(url, Genre[].class);
        Genre[] body = responseEntity.getBody();
        assertEquals(3, body.length);
        assertTrue(Arrays.asList(body).contains(createdGenre));
        restTemplate.delete(url + "/" + createdGenre.getId());
    }

    @Test
    public void shouldUpdateGenre_whenUpdateGenre() {
        ResponseEntity<Genre[]> responseEntity = restTemplate.getForEntity(url, Genre[].class);
        Genre genre = responseEntity.getBody()[0];
        String newGenreTitle = "Story";
        genre.setTitle(newGenreTitle);
        HttpEntity<Genre> request = new HttpEntity<>(genre);

        restTemplate.put(url, request);

        responseEntity = restTemplate.getForEntity(url, Genre[].class);
        Genre updatedGenre = responseEntity.getBody()[0];
        assertEquals(genre, updatedGenre);
    }

    @Test
    public void shouldDeleteGenre_whenDeleteGenre() {
        String genreTitle = "Story";
        HttpEntity<Genre> request = new HttpEntity<>(new Genre(null, genreTitle));
        restTemplate.postForObject(url, request, Void.class);
        ResponseEntity<Genre[]> responseEntity = restTemplate.getForEntity(url, Genre[].class);
        Genre genreToDelete = responseEntity.getBody()[2];
        assertEquals(3, responseEntity.getBody().length);

        restTemplate.delete(url + "/" + genreToDelete.getId());

        responseEntity = restTemplate.getForEntity(url, Genre[].class);
        Genre[] body = responseEntity.getBody();
        assertEquals(2, body.length);
        assertTrue(Arrays.stream(body).noneMatch(genre -> Objects.equals(genreToDelete.getId(), genre.getId())));
    }
}