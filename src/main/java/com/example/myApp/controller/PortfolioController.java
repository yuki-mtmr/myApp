package com.example.myApp.controller;

import com.example.myApp.model.*;
import com.example.myApp.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/portfolios") //"/users"エンドポイント
    public Portfolios selectAll() {
        Portfolios response = new Portfolios();
        ArrayList<Portfolio> list = new ArrayList<>(portfolioService.selectAll());
        response.setPortfolioList(list);
        return response;
    }
}
