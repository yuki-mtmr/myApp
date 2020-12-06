package com.example.myApp.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Users {

    private List<User> userList;

    public List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
