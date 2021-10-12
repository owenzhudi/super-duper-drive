package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES " +
        "(filename, contenttype, filesize, userid, filedata) " +
        "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = ${userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT EXISTS(SELECT * FROM FILES WHERE filename=#{fileName})")
    boolean fileExists(String fileName);

    @Update("UPDATE FILES SET filename = #{fileName}, contentype = #{contentType}, filesize = #{fileSize}, filedata = #{fileData} " +
            "WHERE fileid = #{fileId}")
    boolean updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    boolean deleteFile(Integer fileId);
}
