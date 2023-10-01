package com.homework.library.controller;

import com.homework.library.entity.Genre;
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
    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnAllGenres_whenFindAllGenres() {
        String url = HOST + port + PATH;
        ResponseEntity<Genre[]> responseEntity = this.restTemplate.getForEntity(url, Genre[].class);

        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void shouldAddNewGenre_whenAddGenre() {
        String url = HOST + port + PATH;
        ResponseEntity<Genre[]> responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).length);
        String genreTitle = "Story";
        HttpEntity<Genre> request = new HttpEntity<>(new Genre(null, genreTitle));

        Genre createdGenre = restTemplate.postForObject(url, request, Genre.class);

        responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        Genre[] body = responseEntity.getBody();
        assertEquals(3, Objects.requireNonNull(body).length);
        assertTrue(Arrays.asList(body).contains(createdGenre));
        restTemplate.delete(url + "/" + createdGenre.getId());
    }

    @Test
    public void shouldUpdateGenre_whenUpdateGenre() {
        String url = HOST + port + PATH;
        ResponseEntity<Genre[]> responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        Genre genre = Objects.requireNonNull(responseEntity.getBody())[0];
        String newGenreTitle = "J. Date";
        genre.setGenreTitle(newGenreTitle);
        HttpEntity<Genre> request = new HttpEntity<>(genre);

        restTemplate.put(url, request);

        responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        Genre updatedGenre = Objects.requireNonNull(responseEntity.getBody())[0];
        assertEquals(genre, updatedGenre);
    }

    @Test
    public void shouldDeleteGenre_whenDeleteGenre() {
        String url = HOST + port + PATH;
        String genreTitle = "J. Date";
        HttpEntity<Genre> request = new HttpEntity<>(new Genre(null, genreTitle));
        restTemplate.postForObject(url, request, Void.class);
        ResponseEntity<Genre[]> responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        Genre genreToDelete = Objects.requireNonNull(responseEntity.getBody())[2];
        assertEquals(3, Objects.requireNonNull(responseEntity.getBody()).length);

        restTemplate.delete(url + "/" + genreToDelete.getId());

        responseEntity = this.restTemplate.getForEntity(url, Genre[].class);
        Genre[] body = responseEntity.getBody();
        assertEquals(2, Objects.requireNonNull(body).length);
        assertTrue(Arrays.stream(body).noneMatch(genre -> Objects.equals(genreToDelete.getId(), genre.getId())));
    }
}