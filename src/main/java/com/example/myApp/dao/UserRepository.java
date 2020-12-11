package com.example.myApp.dao;

import com.example.myApp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User select(int id) {
        return userMapper.select(id);
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public int delete(int id) {
        return userMapper.delete(id);
    }
}