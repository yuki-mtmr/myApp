package com.example.myApp.service;

import com.example.myApp.dao.UserWorkRepository;
import com.example.myApp.model.UserWork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class UserWorkService {

    private final UserWorkRepository userWorkRepository;

    @Transactional
    public int insert(UserWork userWork) {
        return userWorkRepository.insert(userWork);
    }

}
