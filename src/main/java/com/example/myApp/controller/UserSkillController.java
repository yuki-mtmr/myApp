package com.example.myApp.controller;


import com.example.myApp.model.UserSkill;
import com.example.myApp.model.UserSkills;
import com.example.myApp.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return response;
    }
}
