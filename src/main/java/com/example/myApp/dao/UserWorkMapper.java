package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import com.example.myApp.model.UserWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserWorkMapper {
    List<UserWork> selectAllWorkByUser(int user_id);
    int insert(UserWork userWork);
    int update(UserWork userWork);
}
