package com.example.myApp.service;

import com.example.myApp.dao.UserWorkRepository;
import com.example.myApp.model.UserSkill;
import com.example.myApp.model.UserWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserWorkService {

    private final UserWorkRepository userWorkRepository;

    @Transactional(readOnly = true)
    public List<UserWork> selectAllWorkByUser(int user_id) {
        return userWorkRepository.selectAllWorkByUser(user_id);
    }

    @Transactional
    public int insert(UserWork userWork) {
        return userWorkRepository.insert(userWork);
    }

    @Transactional
    public int update(UserWork userWork) {
        return userWorkRepository.update(userWork);
    }
}
