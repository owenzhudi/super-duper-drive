package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;

public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS " +
        "(url, username, key, password, userid) " +
        "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    Integer insertCredential(Credential credential);
}
