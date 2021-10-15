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

    // Get the helper code to check file exists from:
    // https://github.com/rrbiz662/super-duper-drive/blob/c4cbf33d6e79503e53337578ff9ee10799a6aa11/starter/cloudstorage/src/main/java/com/udacity/jwdnd/course1/cloudstorage/mapper/FileMapper.java#L31
    @Select("SELECT EXISTS(SELECT * FROM FILES WHERE filename=#{fileName})")
    boolean fileExists(String fileName);

    @Update("UPDATE FILES SET filename = #{fileName}, contentype = #{contentType}, filesize = #{fileSize}, filedata = #{fileData} " +
            "WHERE fileid = #{fileId}")
    boolean updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    boolean deleteFile(Integer fileId);
}
