package com.homework.library.mapper;

import com.homework.library.entity.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select b.id, a.authorName, b.title, g.genreTitle " +
            "from main.books as b " +
            "left join main.authors as a on b.author = a.id " +
            "left join main.genres as g on b.genre = g.id")
    List<Book> findAllBooks();

    @Insert("insert into main.books(id, author,title, genre) values(#{id},#{author},#{title},#{genre})")
    @SelectKey(statement = "select next value for MAIN.BOOKS_SEQUENCE", keyProperty = "id",
            before = true, resultType = Long.class)
    void insert(Book book);
}