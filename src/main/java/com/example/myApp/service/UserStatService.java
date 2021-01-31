package com.example.myApp.service;

import com.example.myApp.dao.UserStatRepository;
import com.example.myApp.model.UserStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserStatService {

    private final UserStatRepository userStatRepository;

    @Transactional(readOnly = true)
    public List<UserStat> selectAllStatusByUser(int user_id) {
        return userStatRepository.selectAllStatusByUser(user_id);
    }

    @Transactional
    public int insert(UserStat userStat) {
        return userStatRepository.insert(userStat);
    }
}
