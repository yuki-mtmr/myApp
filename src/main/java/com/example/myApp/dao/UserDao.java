package com.example.myApp.dao;

import com.example.myApp.model.User;

import java.util.List;

public  interface UserDao {
    List<User> selectAll();
    User select(int id);
    void insert(User User);
    int update(User User);
    int delete(int id);
}