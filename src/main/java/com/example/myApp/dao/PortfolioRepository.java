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

    public Portfolio select(int portfolio_id) {
        return portfolioMapper.select(portfolio_id);
    }

    public int insert(Portfolio portfolio) {
        return portfolioMapper.insert(portfolio);
    }

    public int update(Portfolio portfolio) {
        return portfolioMapper.update(portfolio);
    }

}
