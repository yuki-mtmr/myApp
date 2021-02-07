package com.example.myApp.dao;

import com.example.myApp.model.UserWork;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWorkMapper {
    int insert(UserWork userWork);
}
