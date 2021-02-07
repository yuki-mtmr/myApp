package com.example.myApp.dao;

import com.example.myApp.model.UserWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserWorkRepository {

    private final UserWorkMapper userWorkMapper;

    public int insert(UserWork userWork) {
        return userWorkMapper.insert(userWork);
    }
}
