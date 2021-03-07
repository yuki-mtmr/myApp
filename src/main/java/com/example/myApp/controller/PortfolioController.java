package com.example.myApp.controller;

import com.example.myApp.model.*;
import com.example.myApp.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
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

    @PostMapping("/users/{user_id}/portfolios")
    public CreatePortfoliosRequest create(@PathVariable("user_id") Integer user_id , @Valid @RequestBody CreatePortfoliosRequest post) throws InvocationTargetException, IllegalAccessException {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser_id(user_id);
        BeanUtils.copyProperties(portfolio, post); //フィールドの値を詰め替え
        portfolioService.insert(portfolio);
        return post; //CreatePortfoliosRequestの入力返り値
    }
}
