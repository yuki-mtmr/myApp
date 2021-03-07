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

}

