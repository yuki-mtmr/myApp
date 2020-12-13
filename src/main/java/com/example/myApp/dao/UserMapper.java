package com.example.myApp.dao;

import com.example.myApp.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public  interface UserMapper {
    List<User> selectAll();
    User select(@Param("id") int id);
    int insert(@Param("user")User user);
    int update(@Param("user")User user);
    int delete(@Param("id")int id);
}
