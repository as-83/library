package com.homework.library;

import com.homework.library.entity.Author;
import com.homework.library.entity.Book;
import com.homework.library.entity.Genre;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MappedTypes({Book.class, Author.class, Genre.class})
@MapperScan("com.homework.library.mapper")
@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
