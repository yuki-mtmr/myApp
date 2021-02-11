package com.example.myApp.dao;

import com.example.myApp.model.UserStat;
import com.example.myApp.model.UserWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserWorkRepository {

    private final UserWorkMapper userWorkMapper;

    public List<UserWork> selectAllWorkByUser(int user_id) {
        return userWorkMapper.selectAllWorkByUser(user_id);
    }

    public int insert(UserWork userWork) {
        return userWorkMapper.insert(userWork);
    }

    public int update(UserWork userWork) {
        return userWorkMapper.update(userWork);
    }
}
