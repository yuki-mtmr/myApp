package com.example.myApp.dbunit.domain.service;

import com.example.myApp.dao.PortfolioRepository;
import com.example.myApp.dbunit.dataset.CsvDataSetLoader;
import com.example.myApp.model.Portfolio;
import com.example.myApp.model.UserSkill;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
class PortfolioServiceTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    private final String userDate = "2020-12-06 10:10:59";
    private final  SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    @DatabaseSetup("/testdata/portfolioServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/portfolioServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void findAll() {
        // 検索結果
        List<Portfolio> portfolios = portfolioRepository.selectAll();
        Assertions.assertEquals(4, portfolios.size());
    }

    @Test
    @DatabaseSetup("/testdata/portfolioServiceTest/init-data") // テスト実行前に初期データを投入
    @ExpectedDatabase(value = "/testdata/portfolioServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
    void findByPk() {
        // portfolio_id=2のデータの期待値
        Portfolio expect = new Portfolio();
        expect.setPortfolio_id(2);
        expect.setUser_id(2);
        expect.setPortfolioName("test2");
        expect.setPortfolioPic("pic2");
        expect.setIntroduction("introduction2");
        try {
            expect.setCreatedAt(sdFormat.parse(userDate));
            expect.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // portfolio_id=2の検索結果
        Portfolio actual = portfolioRepository.select(2);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }

    //新規投稿のテスト
    @Test
    @DatabaseSetup("/testdata/portfolioServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/portfolioServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        Portfolio newPortfolio = new Portfolio();
        newPortfolio.setPortfolio_id(5);
        newPortfolio.setUser_id(5);
        newPortfolio.setPortfolioName("newPortfolio");
        newPortfolio.setPortfolioPic("newPic");
        newPortfolio.setIntroduction("newIntroduction");

        //登録実行
        int actual = portfolioRepository.insert(newPortfolio);
        //検証：1件の追加に成功していること
        Assertions.assertEquals(1, actual);
    }

    @Test
    @DatabaseSetup("/testdata/portfolioServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/portfolioServiceTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが更新されていること
    void update() {
        // 更新するデータを準備
        Portfolio updatePortfolio = new Portfolio();
        updatePortfolio.setPortfolio_id(4);
        updatePortfolio.setPortfolioName("updatePortfolioName");
        updatePortfolio.setPortfolioPic("updatePortfolioPic");
        updatePortfolio.setIntroduction("updateIntroduction");

        // 更新実行
        int updatedCount = portfolioRepository.update(updatePortfolio);
        // 検証：1件の更新に成功していること
        Assertions.assertEquals(1, updatedCount);
    }
}
