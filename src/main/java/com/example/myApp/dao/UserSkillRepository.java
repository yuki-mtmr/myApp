package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSkillRepository {

    private final UserSkillMapper userSkillMapper;

    public List<UserSkill> selectAll() {
        return userSkillMapper.selectAll();
    }
}
