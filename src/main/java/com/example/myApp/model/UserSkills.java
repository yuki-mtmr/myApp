package com.example.myApp.model;

import com.example.myApp.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserSkills {

    private List<UserSkill> userSkillList;

    public List<UserSkill> getUserSkillList() {
        if (userSkillList == null) {
            userSkillList = new ArrayList<>();
        }
        return userSkillList;
    }

    public void setUserSkillList(List<UserSkill> userSkillList) {
        this.userSkillList = userSkillList;
    }
}
