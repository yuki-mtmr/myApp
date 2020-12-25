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

    @GetMapping("/userSkills") //"/userSkills"エンドポイント
    public UserSkills selectAll() {
        UserSkills response = new UserSkills();
        ArrayList<UserSkill> list = new ArrayList<>(userSkillService.selectAll());
        response.setUserSkillList(list);
        return response;
    }
}