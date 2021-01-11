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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @GetMapping("/users/{user_id}/userSkills") //"/userSkills"エンドポイント
    public UserSkills selectAllSkillByUser(@PathVariable("user_id") Integer user_id) {
        UserSkills response = new UserSkills();
        ArrayList<UserSkill> list = new ArrayList<>(userSkillService.selectAllSkillByUser(user_id));
        response.setUserSkillList(list);
        if (list.size() == 0) {
            throw new RecordNotFoundException("Invalid user_id : " + user_id);
        }
        return response;
    }

    @PostMapping("/users/{user_id}/userSkills")
    public CreateUserSkillsRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreateUserSkillsRequest post) throws InvocationTargetException, IllegalAccessException {
        UserSkill userSkill = new UserSkill();
        userSkill.setUser_id(user_id);
        BeanUtils.copyProperties(userSkill, post); //フィールドの値を詰め替え
        userSkillService.insert(userSkill);
        return post; //CreateUserSkillsRequestの入力返り値
    }

    @PutMapping("/users/userSkills/{skill_id}")
    public Map<String,String> update(@PathVariable("skill_id") Integer skill_id, @Valid @RequestBody CreateUserSkillsRequest post) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> results = new HashMap<>();
        UserSkill userSkill = new UserSkill();
        BeanUtils.copyProperties(userSkill, post);
        userSkill.setSkill_id(skill_id);
        int count = userSkillService.update(userSkill);
        results.put("result", count == 1 ? "OK" : "NG");
        return results;
    }
}
