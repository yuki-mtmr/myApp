package com.example.myApp.controller;

import com.example.myApp.model.*;
import com.example.myApp.service.UserStatService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserStatController {

    @Autowired
    private UserStatService userStatService;

    @PostMapping("/users/{user_id}/userStats")
    public CreateUserStatsRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreateUserStatsRequest post) throws InvocationTargetException, IllegalAccessException {
        UserStat userStat = new UserStat();
        userStat.setUser_id(user_id);
        BeanUtils.copyProperties(userStat, post); //フィールドの値を詰め替え
        userStatService.insert(userStat);
        return post; //CreateUserStatsRequestの入力返り値
    }
}
