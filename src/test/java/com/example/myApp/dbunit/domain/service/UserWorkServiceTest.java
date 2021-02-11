package com.example.myApp.dbunit.domain.service;

import com.example.myApp.dao.UserWorkRepository;
import com.example.myApp.dbunit.dataset.CsvDataSetLoader;
import com.example.myApp.model.UserSkill;
import com.example.myApp.model.UserWork;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
public class UserWorkServiceTest {

    @Autowired
    private UserWorkRepository userWorkRepository;

    private final String userDate = "2020-12-06 10:10:59";
    private final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    @DatabaseSetup("/testdata/userWorkServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userWorkServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void findAll() {
        // 検索結果
        List<UserWork> userWorks = userWorkRepository.selectAllWorkByUser(1);
        Assertions.assertEquals(2, userWorks.size());

        // user_id=1のデータの期待値
        UserWork expect1 = new UserWork();
        expect1.setWork_id(1);
        expect1.setUser_id(1);
        expect1.setWorkThumbnail("pic1");
        expect1.setWorkLink("test1");
        expect1.setWorkDetail("test1");
        try {
            expect1.setCreatedAt(sdFormat.parse(userDate));
            expect1.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        UserWork expect2 = new UserWork();
        expect2.setWork_id(2);
        expect2.setUser_id(1);
        expect2.setWorkThumbnail("pic2");
        expect2.setWorkLink("test2");
        expect2.setWorkDetail("test2");
        try {
            expect2.setCreatedAt(sdFormat.parse(userDate));
            expect2.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<UserWork> expect = new ArrayList<UserWork>(Arrays.asList(expect1, expect2));
        // user_id=1の検索結果
        List<UserWork> actual = userWorkRepository.selectAllWorkByUser(1);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }

    //新規投稿のテスト
    @Test
    @DatabaseSetup("/testdata/userWorkServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userWorkServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        UserWork newWork = new UserWork();
        newWork.setWork_id(5);
        newWork.setUser_id(1);
        newWork.setWorkThumbnail("pic5");
        newWork.setWorkLink("test5");
        newWork.setWorkDetail("test5");

        //登録実行
        int actual = userWorkRepository.insert(newWork);
        //検証：1件の追加に成功していること
        Assertions.assertEquals(1, actual);
    }
}
