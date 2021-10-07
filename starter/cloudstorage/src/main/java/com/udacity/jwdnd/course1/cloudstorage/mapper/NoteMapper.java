package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES " +
        "(notetitle, notedescription, userid) " +
        "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
    Note getNote(String noteTitle);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

    @Update("UPDATE NOTES SET " +
        "notetitle = #{noteTitle}, " +
        "notedescription = #{noteDescription}, " +
        "userid = #{userId} " +
        "WHERE noteid = #{noteId}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    boolean deleteNote(Integer noteId);

}
