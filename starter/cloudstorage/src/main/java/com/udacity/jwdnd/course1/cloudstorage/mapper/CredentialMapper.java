package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS " +
        "(url, username, key, password, userid) " +
        "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    Integer insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> getCredentials(Integer userId);

    @Update("UPDATE CREDENTIALS SET "
            + "url = #{url}, "
            + "username = #{userName}, "
            + "key = #{key}, "
            + "password = #{password} "
            + "WHERE credentialid = #{credentialId}")
    boolean updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    boolean deleteCredential(Integer credentialId);
}