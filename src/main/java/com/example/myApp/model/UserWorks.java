package com.example.myApp.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserWorks {

    private List<UserWork> userWorkList;

    public List<UserWork> getUserWorkList() {
        if (userWorkList == null) {
            userWorkList = new ArrayList<>();
        }
        return userWorkList;
    }

    public void setUserWorkList(List<UserWork> userWorkList) {
        this.userWorkList = userWorkList;
    }
}
