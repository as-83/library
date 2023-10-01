package com.homework.library.controller;

import com.homework.library.entity.Book;
import com.homework.library.mapper.AuthorMapper;
import com.homework.library.mapper.BookMapper;
import com.homework.library.mapper.GenreMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(hierarchyMode = DirtiesContext.HierarchyMode.EXHAUSTIVE)
class BookControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private GenreMapper genreMapper;


    @Test
    public void shouldReturnAllBooks_whenGetBooks() {
        when(bookMapper.findAllBooks()).thenReturn(List.of(new Book(1L, "", "", ""), new Book(2L, "", "", "")));
        ResponseEntity<Book[]> responseEntity = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/api/books",
                Book[].class
        );


        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).length);
    }
}