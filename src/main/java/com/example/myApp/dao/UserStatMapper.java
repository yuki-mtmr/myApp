package com.example.myApp.dao;

import com.example.myApp.model.UserStat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserStatMapper {
    List<UserStat> selectAllStatusByUser(int user_id);
    int insert(UserStat userStat);
    int update(UserStat userStat);
}
