package com.homework.library.mapper;

import com.homework.library.entity.Genre;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GenreMapper {

    @Select("select * from main.genres")
    List<Genre> findAllGenres();

    @Insert("insert into main.genres(id, title) values(#{id},#{title})")
    @SelectKey(statement = "select next value for main.genres_sequence", keyProperty = "id",
            before = true, resultType = Long.class)
    void addGenre(Genre genre);

    @Update("update main.genres set title = #{title} where id = #{id}")
    void updateGenre(Genre genre);

    @Delete("delete from main.genres where id = #{id}")
    void deleteGenre(Long id);

    @Select("select id from main.genres where title=#{genreTitle}")
    Long getGenreIdByTitle(String genreTitle);
}