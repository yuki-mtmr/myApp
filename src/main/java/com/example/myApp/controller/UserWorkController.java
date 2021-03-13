package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.RecordNotFoundException;
import com.example.myApp.model.*;
import com.example.myApp.service.UserWorkService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserWorkController {

    private final UserWorkService userWorkService;

    @GetMapping("/users/{user_id}/userWorks") //"/userWorks"エンドポイント
    public UserWorks selectAllWorkByUser(@PathVariable("user_id") Integer user_id) {
        UserWorks response = new UserWorks();
        ArrayList<UserWork> list = new ArrayList<>(userWorkService.selectAllWorkByUser(user_id));
        response.setUserWorkList(list);
        if (list.size() == 0) {
            throw new RecordNotFoundException("Invalid user_id : " + user_id);
        }
        return response;
    }

    @PostMapping("/users/{user_id}/userWorks")
    public CreateUserWorksRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreateUserWorksRequest post) throws InvocationTargetException, IllegalAccessException {
        UserWork userWork = new UserWork();
        userWork.setUser_id(user_id);
        BeanUtils.copyProperties(userWork, post); //フィールドの値を詰め替え
        userWorkService.insert(userWork);
        return post; //CreateUserWorksRequestの入力返り値
    }

    @PutMapping("/users/userWorks/{work_id}")
    public Map<String,String> update(@PathVariable("work_id") Integer work_id, @Valid @RequestBody CreateUserWorksRequest post) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> results = new HashMap<>();
        UserWork userWork = new UserWork();
        BeanUtils.copyProperties(userWork, post);
        userWork.setWork_id(work_id);
        int count = userWorkService.update(userWork);
        results.put("result", count == 1 ? "OK" : "NG");
        return results;
    }
}
