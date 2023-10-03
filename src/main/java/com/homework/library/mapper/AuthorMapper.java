package com.homework.library.mapper;

import com.homework.library.entity.Author;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthorMapper {

    @Select("select * from main.authors")
    List<Author> findAllAuthors();

    @Insert("insert into main.authors(id, name) values(#{id}, #{name})")
    @SelectKey(statement = "select next value for main.authors_sequence", keyProperty = "id",
            before = true, resultType = Long.class)
    void addAuthor(Author author);

    @Update("update main.authors set name = #{name} where id = #{id}")
    void updateAuthor(Author author);

    @Delete("delete from main.authors where id = #{id}")
    void deleteAuthor(Long id);

    @Select("select id from main.authors where name=#{name}")
    Long getAuthorIdByName(String authorName);
}