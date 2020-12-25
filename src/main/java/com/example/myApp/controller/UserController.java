package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.RecordNotFoundException;
import com.example.myApp.model.CreateUsersRequest;
import com.example.myApp.model.User;

import com.example.myApp.model.Users;
import com.example.myApp.dao.UserRepository;
import com.example.myApp.service.UserService;
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
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/users") //"/users"エンドポイント
    public Users selectAll() {
        Users response = new Users();
        ArrayList<User> list = new ArrayList<>(userService.selectAll());
        response.setUserList(list);
        return response;
    }

    @GetMapping("/users/{id}")
    public User select(@PathVariable("id") Integer id) {
        User user = userService.select(id);
        if(user == null ) {
            throw new RecordNotFoundException("Invalid users_id : " + id);
        }
        return user;
    }

    @PostMapping("/users")
    public CreateUsersRequest create(@Valid @RequestBody CreateUsersRequest post) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        BeanUtils.copyProperties(user, post); //フィールドの値を詰め替え
        userService.insert(user);
        return post; //CreateUsersRequestの入力返り値
    }

    @PutMapping("/users/{id}")
    public Map<String,String> update(@PathVariable("id") Integer id,@Valid @RequestBody CreateUsersRequest post) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> results = new HashMap<>();
        User user = new User();
        BeanUtils.copyProperties(user, post);
        user.setId(id);
        int count = userService.update(user);
        results.put("result", count == 1 ? "OK" : "NG");
        return results;
    }

    @DeleteMapping("/users/{id}")
    public Map<String,String> delete(@PathVariable("id") Integer id) {
        Map<String,String> results = new HashMap<>();
        int count = userService.delete(id);
        results.put("result", count == 1 ? "OK" : "NG");
        return results;
    }
}