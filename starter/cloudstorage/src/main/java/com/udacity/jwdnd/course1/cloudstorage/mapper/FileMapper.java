package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES " +
        "(filename, contenttype, filesize, userid, filedata) " +
        "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    Integer insertFile(File file);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Update("UPDATE FILES SET " +
        "filename = #{fileName} " +
        "content")
    boolean updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    boolean deleteFile(Integer fileId);
}
