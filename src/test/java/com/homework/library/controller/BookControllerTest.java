package com.homework.library.controller;

import com.homework.library.entity.Book;
import com.homework.library.mapper.AuthorMapper;
import com.homework.library.mapper.BookMapper;
import com.homework.library.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.CURRENT_LEVEL)
class BookControllerTest {
    @Value(value = "${local.server.port}")
    private int port;
    private String url;
    private List<Book> books;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private GenreMapper genreMapper;

    @BeforeEach
    void setup() {
        url = "http://localhost:" + port + "/v1/api/books";
        books = List.of(
                new Book(1L, "Author1", "Title1", "Genre1"),
                new Book(2L, "Author1", "Title2", "Genre1")
        );
    }

    @Test
    public void shouldReturnAllBooks_whenGetBooks_noParameters() {
        when(bookMapper.getBooksByAuthorAndGenreDynamic(null, null)).thenReturn(books);
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(
                url,
                Book[].class
        );

        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void shouldReturnFilteredBooks_whenGetBooks_givenAuthorAndGenre() {
        String author = "Bob Martin";
        String genre = "Programming";

        Map<String, String> params = new HashMap<>();
        params.put("author", author);
        params.put("genre", genre);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("author", "{author}")
                .queryParam("genre", "{genre}")
                .encode()
                .toUriString();
        when(authorMapper.getAuthorIdByName(author)).thenReturn(1L);
        when(genreMapper.getGenreIdByTitle(genre)).thenReturn(1L);
        when(bookMapper.getBooksByAuthorAndGenreDynamic(1L, 1L)).thenReturn(books);
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(
                urlTemplate,
                Book[].class,
                params
        );

        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void shouldReturnErrorMessage_whenGetBooks_givenNotExistedAuthor() {
        String notExistedAuthor = "Wrong Author";

        Map<String, String> params = new HashMap<>();
        params.put("author", notExistedAuthor);
        params.put("genre", "Story");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("author", "{author}")
                .queryParam("genre", "{genre}")
                .encode()
                .toUriString();
        when(authorMapper.getAuthorIdByName(notExistedAuthor)).thenReturn(null);

        String message = restTemplate.getForObject(urlTemplate, String.class, params);

        assertEquals("Author with this name doesnt exist: " + notExistedAuthor, message);
    }

    @Test
    public void shouldReturnErrorMessage_whenGetBooks_givenNotExistedGenre() {
        String notExistedGenre = "Wrong Author";

        Map<String, String> params = new HashMap<>();
        params.put("genre", notExistedGenre);
        params.put("author", "Bob Martin");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("author", "{author}")
                .queryParam("genre", "{genre}")
                .encode()
                .toUriString();
        when(genreMapper.getGenreIdByTitle(notExistedGenre)).thenReturn(null);

        String message = restTemplate.getForObject(urlTemplate, String.class, params);

        assertEquals("Genre with this title doesnt exist: " + notExistedGenre, message);
    }
}