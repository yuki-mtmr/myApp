package com.example.myApp.service;

import com.example.myApp.dao.UserSkillRepository;
import com.example.myApp.model.UserSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;

    @Transactional(readOnly = true)
    public List<UserSkill> selectAllSkillByUser(int user_id) {
        return userSkillRepository.selectAllSkillByUser(user_id);
    }

    @Transactional
    public int insert(UserSkill userSkill) {
        return userSkillRepository.insert(userSkill);
    }

    @Transactional
    public int update(UserSkill userSkill) {
        return userSkillRepository.update(userSkill);
    }
}
