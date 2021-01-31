package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.example.myApp.model.UserStat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserStatRepository {

    private final UserStatMapper userStatMapper;

    public List<UserStat> selectAllStatusByUser(int user_id) {
        return userStatMapper.selectAllStatusByUser(user_id);
    }

    public int insert(UserStat userStat) {
        return userStatMapper.insert(userStat);
    }

    public int update(UserStat userStat) {
        return userStatMapper.update(userStat);
    }
}
