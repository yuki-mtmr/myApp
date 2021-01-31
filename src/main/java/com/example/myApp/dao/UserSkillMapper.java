package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSkillMapper {
    List<UserSkill> selectAllSkillByUser(int user_id);
    int insert(UserSkill userSkill);
    int update(UserSkill userSkill);
}
