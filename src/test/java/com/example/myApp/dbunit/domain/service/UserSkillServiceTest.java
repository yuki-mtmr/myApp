package com.example.myApp.dbunit.domain.service;

import com.example.myApp.dao.UserSkillRepository;
import com.example.myApp.dbunit.dataset.CsvDataSetLoader;
import com.example.myApp.model.UserSkill;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
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
@RunWith(SpringRunner.class)
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。＊CsvDataSetLoaderクラスは自作します（後述）
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
class UserSkillServiceTest {

    @Autowired
    private UserSkillRepository userSkillRepository;

    private final String userDate = "2020-12-06 10:10:59";
    private final  SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    @DatabaseSetup("/testdata/userSkillServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userSkillServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void findAll() {
        // 検索結果
        List<UserSkill> userSkills = userSkillRepository.selectAllSkill(1);
        Assertions.assertEquals(2, userSkills.size());

        // user_id=1のデータの期待値
        UserSkill expect1 = new UserSkill();
        expect1.setSkill_id(1);
        expect1.setUser_id(1);
        expect1.setSkillName("ruby");
        expect1.setSkillLevel(8);
        expect1.setSkillDetail("test");
        try {
            expect1.setCreatedAt(sdFormat.parse(userDate));
            expect1.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        UserSkill expect2 = new UserSkill();
        expect2.setSkill_id(2);
        expect2.setUser_id(1);
        expect2.setSkillName("java");
        expect2.setSkillLevel(9);
        expect2.setSkillDetail("test");
        try {
            expect2.setCreatedAt(sdFormat.parse(userDate));
            expect2.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<UserSkill> expect = new ArrayList<UserSkill>(Arrays.asList(expect1, expect2));
        // user_id=1の検索結果
        List<UserSkill> actual = userSkillRepository.selectAllSkill(1);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }
}
