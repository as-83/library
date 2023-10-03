package com.homework.library.mapper;

import com.homework.library.entity.Author;
import com.homework.library.entity.Book;
import com.homework.library.entity.Genre;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Insert("insert into main.books(id, author, title, genre) " +
            "values(#{book.id}, #{authorId}, #{book.title}, #{genreId})")
    @SelectKey(statement = "select next value for main.books_sequence", keyProperty = "book.id",
            before = true, resultType = Long.class)
    void insert(@Param("book") Book book, Long authorId, Long genreId);

    @Select(
            "<script>" +
                    "select b.id, a.name as author, b.title, g.title as genre " +
                    "from main.books as b " +
                    "left join main.authors as a on b.author = a.id " +
                    "left join main.genres as g on b.genre = g.id " +
                    "where 1=1  " +
                    "<if test=\"authorId !=null \"> and b.author=#{authorId}</if> " +
                    "<if test=\"genreId !=null \"> and b.genre=#{genreId}</if>" +
            "</script>"
    )
    List<Book> getBooksByAuthorAndGenreDynamic(Long authorId, Long genreId);

    @Select(
            "select b.id, a.name, b.title, g.title " +
                    "from main.books as b " +
                    "left join main.authors as a on b.author = a.id " +
                    "left join main.genres as g on b.genre = g.id " +
                    "where b.author=#{authorId}"
    )
    
    List<Book> getBooksByAuthorName(Long authorId);

    @Update(
            "update main.books " +
                    "set author = #{authorId}, title = #{book.title}, genre = #{genreId} " +
                    "where id = #{book.id}"
    )
    void updateBook(Book book, Long authorId, Long genreId);

    @Delete("delete from main.books where id = #{id}")
    void deleteBook(Long id);
}