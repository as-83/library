package com.homework.library.mapper;

import com.homework.library.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select b.id, a.authorName, b.title, g.genreTitle " +
            "from main.books as b " +
            "left join main.authors as a on b.author = a.id " +
            "left join main.genres as g on b.genre = g.id")
    List<Book> findAllBooks();

    @Insert("insert into main.books(id, author,title, genre) values(#{id},#{authorId},#{book.title},#{genreId})")
    @SelectKey(statement = "select next value for main.books_sequence", keyProperty = "id",
            before = true, resultType = Long.class)
    void insert(Book book, Long authorId, Long genreId);

    @Select(
            "select b.id, a.authorName, b.title, g.genreTitle " +
                    "from main.books as b " +
                    "left join main.authors as a on b.author = a.id " +
                    "left join main.genres as g on b.genre = g.id" +
                    " where b.author=#{authorId} and b.genre=#{genreId}"
    )
    List<Book> getBooksByAuthorAndGenre(Long authorId, Long genreId);

    @Select(
            "select b.id, a.authorName, b.title, g.genreTitle " +
                    "from main.books as b " +
                    "left join main.authors as a on b.author = a.id " +
                    "left join main.genres as g on b.genre = g.id" +
                    " where b.genre=#{genreId}"
    )
    List<Book> getBooksByGenreId(Long genreId);

    @Select(
            "select b.id, a.authorName, b.title, g.genreTitle " +
                    "from main.books as b " +
                    "left join main.authors as a on b.author = a.id " +
                    "left join main.genres as g on b.genre = g.id" +
                    " where b.author=#{authorId}"
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