package com.example.myApp.service;

import com.example.myApp.dao.UserRepository;
import com.example.myApp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> selectAll() {
        return userRepository.selectAll();
    }

    @Transactional(readOnly = true)
    public User select(int id) {
        return userRepository.select(id);
    }

    @Transactional
    public int insert(User user) {
        return userRepository.insert(user);
    }

    @Transactional
    public int update(User user) {
        return userRepository.update(user);
    }

    @Transactional
    public int delete(int id) {
        return userRepository.delete(id);
    }

}

