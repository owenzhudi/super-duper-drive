package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES " +
        "(notetitle, notedescription, userid) " +
        "VALUES (#{noteTitle}, #{noteDescription} #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
    Note getNote(String noteTitle);

    @Update("UPDATE NOTES SET" +
        "notetitle = #{noteTitle}, " +
        "noteDescription = #{noteDescription}, " +
        "userId = #{userId}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE notetitle = #{noteTitle}")
    boolean deleteNote(String noteTitle);

}
