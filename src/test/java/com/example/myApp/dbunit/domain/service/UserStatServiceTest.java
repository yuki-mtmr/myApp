package com.example.myApp.dbunit.domain.service;

import com.example.myApp.dao.UserStatRepository;
import com.example.myApp.dbunit.dataset.CsvDataSetLoader;
import com.example.myApp.model.UserStat;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
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

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import java.text.SimpleDateFormat;

@Slf4j
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
public class UserStatServiceTest {

    @Autowired
    private UserStatRepository userStatRepository;

    private final String userDate = "2020-12-06 10:10:59";
    private final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //新規投稿のテスト
    @Test
    @DatabaseSetup("/testdata/userStatServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userStatServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        UserStat newStat = new UserStat();
        newStat.setStatus_id(3);
        newStat.setUser_id(2);
        newStat.setStatusName("time");
        newStat.setStatusVolume(20);

        //登録実行
        int actual = userStatRepository.insert(newStat);
        //検証：1件の追加に成功していること
        Assertions.assertEquals(1, actual);
    }
}
