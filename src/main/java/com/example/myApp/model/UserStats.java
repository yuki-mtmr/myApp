package com.example.myApp.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserStats {
    private List<UserStat> userStatList;

    public List<UserStat> getUserStatList() {
        if (userStatList == null) {
            userStatList = new ArrayList<>();
        }
        return userStatList;
    }

    public void setUserStatList(List<UserStat> userStatList) {
        this.userStatList = userStatList;
    }
}
