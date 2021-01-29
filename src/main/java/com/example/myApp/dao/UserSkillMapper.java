package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;

import java.util.List;

public interface UserSkillMapper {
    List<UserSkill> selectAllSkillByUser(int user_id);
    int insert(UserSkill userSkill);
    int update(UserSkill userSkill);
}
