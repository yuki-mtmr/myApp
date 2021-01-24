package com.example.myApp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.example.myApp.model.UserStat;

@Repository
@RequiredArgsConstructor
public class UserStatRepository {

    private final UserStatMapper userStatMapper;

    public int insert(UserStat userStat) {
        return userStatMapper.insert(userStat);
    }
}
