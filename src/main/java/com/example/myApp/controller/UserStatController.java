package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.RecordNotFoundException;
import com.example.myApp.model.*;
import com.example.myApp.service.UserStatService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserStatController {

    private final UserStatService userStatService;

    @GetMapping("/users/{user_id}/userStats") //"/userStats"エンドポイント
    public UserStats selectAllStatusByUser(@PathVariable("user_id") Integer user_id) {
        UserStats response = new UserStats();
        ArrayList<UserStat> list = new ArrayList<>(userStatService.selectAllStatusByUser(user_id));
        response.setUserStatList(list);
        if (list.size() == 0) {
            throw new RecordNotFoundException("Invalid user_id : " + user_id);
        }
        return response;
    }

    @PostMapping("/users/{user_id}/userStats")
    public CreateUserStatsRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreateUserStatsRequest post) throws InvocationTargetException, IllegalAccessException {
        UserStat userStat = new UserStat();
        userStat.setUser_id(user_id);
        BeanUtils.copyProperties(userStat, post); //フィールドの値を詰め替え
        userStatService.insert(userStat);
        return post; //CreateUserStatsRequestの入力返り値
    }

    @PutMapping("/users/userStats/{status_id}")
    public Map<String,String> update(@PathVariable("status_id") Integer status_id, @Valid @RequestBody CreateUserStatsRequest post) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> results = new HashMap<>();
        UserStat userStat = new UserStat();
        BeanUtils.copyProperties(userStat, post);
        userStat.setStatus_id(status_id);
        int count = userStatService.update(userStat);
        results.put("result", count == 1 ? "OK" : "NG");
        return results;
    }
}
