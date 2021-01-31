package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import com.example.myApp.model.UserStat;

import java.util.List;

public interface UserStatMapper {
    List<UserStat> selectAllStatusByUser(int user_id);
    int insert(UserStat userStat);
    int update(UserStat userStat);
}
