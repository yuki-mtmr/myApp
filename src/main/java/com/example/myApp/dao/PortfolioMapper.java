package com.example.myApp.dao;

import com.example.myApp.model.Portfolio;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public  interface PortfolioMapper {
    List<Portfolio> selectAll();
    Portfolio select(int portfolio_id);
    int insert(Portfolio portfolio);
}
