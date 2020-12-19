package com.example.myApp.dao;

import com.example.myApp.model.User;

import java.util.List;

public  interface UserMapper {
    List<User> selectAll();
    User select(int id);
    int insert(User user);
    int update(User user);
    int delete(int id);
}
