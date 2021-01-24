package com.example.myApp.dao;

import com.example.myApp.model.UserSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserSkillRepository {

    private final UserSkillMapper userSkillMapper;

    public List<UserSkill> selectAllSkillByUser(int user_id) {
        return userSkillMapper.selectAllSkillByUser(user_id);
    }

    public int insert(UserSkill userSkill) {
        return userSkillMapper.insert(userSkill);
    }

    public int update(UserSkill userSkill) {
        return userSkillMapper.update(userSkill);
    }
}
