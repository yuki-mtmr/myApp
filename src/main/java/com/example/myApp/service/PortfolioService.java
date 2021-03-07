package com.example.myApp.service;

import com.example.myApp.dao.PortfolioRepository;
import com.example.myApp.model.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public List<Portfolio> selectAll() {
        return portfolioRepository.selectAll();
    }

    @Transactional(readOnly = true)
    public Portfolio select(int portfolio_id) {
        return portfolioRepository.select(portfolio_id);
    }

    @Transactional
    public int insert(Portfolio portfolio) {
        return portfolioRepository.insert(portfolio);
    }

}
