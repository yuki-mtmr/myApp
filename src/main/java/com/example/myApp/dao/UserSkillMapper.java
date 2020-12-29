package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;

import java.util.List;

public  interface UserSkillMapper {
    List<UserSkill> selectAllSkillByUser(int id);
}

