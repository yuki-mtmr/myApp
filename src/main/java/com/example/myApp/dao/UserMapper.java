package com.example.myApp.dao;

import com.example.myApp.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public  interface UserMapper {
    List<User> selectAll();
    User select(int id);
    int insert(User user);
    int update(User user);
    int delete(int id);
}
