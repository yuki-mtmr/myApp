package com.example.myApp.dao;

import com.example.myApp.model.Portfolio;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public  interface PortfolioMapper {
    List<Portfolio> selectAll();
    int insert(Portfolio portfolio);
}
