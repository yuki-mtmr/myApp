package com.example.myApp.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Portfolios {

    private List<Portfolio> portfolioList;

    public List<Portfolio> getPortfolioList() {
        if (portfolioList == null) {
            portfolioList = new ArrayList<>();
        }
        return portfolioList;
    }

    public void setPortfolioList(List<Portfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }
}
