package com.example.myApp.service;

import com.example.myApp.dao.UserStatRepository;
import com.example.myApp.model.UserStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserStatService {

    private final UserStatRepository userStatRepository;

    @Transactional
    public int insert(UserStat userStat) {
        return userStatRepository.insert(userStat);
    }
}
