package com.example.myApp.controller;


import com.example.myApp.ErrorHandler.RecordNotFoundException;
import com.example.myApp.model.*;
import com.example.myApp.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @GetMapping("/users/{id}/userSkills") //"/userSkills"エンドポイント
    public UserSkills selectAllSkillByUser(@PathVariable("id") Integer id) {
        UserSkills response = new UserSkills();
        ArrayList<UserSkill> list = new ArrayList<>(userSkillService.selectAllSkillByUser(id));
        response.setUserSkillList(list);
        if (list.size() == 0) {
            throw new RecordNotFoundException("Invalid users_id : " + id);
        }
        return response;
    }

    @PostMapping("/users/{id}/userSkills")
    public CreateUserSkillsRequest create(@PathVariable("id") Integer id , @Valid @RequestBody CreateUserSkillsRequest post) throws InvocationTargetException, IllegalAccessException {
        UserSkill userSkill = new UserSkill();
        userSkill.setUser_id(id);
        BeanUtils.copyProperties(userSkill, post); //フィールドの値を詰め替え
        userSkillService.insert(userSkill);
        return post; //CreateUserSkillsRequestの入力返り値
    }
}
