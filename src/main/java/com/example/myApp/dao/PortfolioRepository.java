package com.example.myApp.dao;

import com.example.myApp.model.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {

    private final PortfolioMapper portfolioMapper;

    public List<Portfolio> selectAll() {
        return portfolioMapper.selectAll();
    }

    public int insert(Portfolio portfolio) {
        return portfolioMapper.insert(portfolio);
    }

}
