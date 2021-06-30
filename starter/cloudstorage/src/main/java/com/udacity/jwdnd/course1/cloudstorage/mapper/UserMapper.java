package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USERS + " +
            "(username, salt, password, firstname, lastname)" +
            "VALUES" + "(#{userName}, #{salt}, #{password}), #{firstName}, #{lastName}")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Update("UPDATE USERS SET " +
        "username = #{userName}," +
        "salt = #{salt}," +
        "password = #{password}," +
        "firstname = #{firstName}," +
        "lastName = #{lastName} " +
        "WHERE username = #{userName}")
    boolean updateUser(User user);

    @Delete("DELETE FROM USERS WHERE username = #{userName}")
    boolean deleteUser(String userName);
}
