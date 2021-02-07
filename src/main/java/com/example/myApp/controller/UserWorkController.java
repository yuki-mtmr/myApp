package com.example.myApp.controller;

import com.example.myApp.model.CreateUserWorksRequest;
import com.example.myApp.model.UserWork;
import com.example.myApp.service.UserWorkService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserWorkController {

    private final UserWorkService userWorkService;

    @PostMapping("/users/{user_id}/userWorks")
    public CreateUserWorksRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreateUserWorksRequest post) throws InvocationTargetException, IllegalAccessException {
        UserWork userWork = new UserWork();
        userWork.setUser_id(user_id);
        BeanUtils.copyProperties(userWork, post); //フィールドの値を詰め替え
        userWorkService.insert(userWork);
        return post; //CreateUserWorksRequestの入力返り値
    }
}
