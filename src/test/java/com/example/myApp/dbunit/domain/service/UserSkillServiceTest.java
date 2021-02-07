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
        List<UserSkill> userSkills = userSkillRepository.selectAllSkillByUser(1);
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
        List<UserSkill> actual = userSkillRepository.selectAllSkillByUser(1);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }

    //新規投稿のテスト
    @Test
    @DatabaseSetup("/testdata/userSkillServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userSkillServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        UserSkill newSkill = new UserSkill();
        newSkill.setSkill_id(5);
        newSkill.setUser_id(1);
        newSkill.setSkillName("Vue");
        newSkill.setSkillLevel(8);
        newSkill.setSkillDetail("test");

        //登録実行
        int actual = userSkillRepository.insert(newSkill);
        //検証：1件の追加に成功していること
        Assertions.assertEquals(1, actual);
    }

    @Test
    @DatabaseSetup("/testdata/userSkillServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userSkillServiceTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが更新されていること
    void update() {
        // 更新するデータを準備
        UserSkill updateUserSkill = new UserSkill();
        updateUserSkill.setSkill_id(4);
        updateUserSkill.setSkillName("Vue");
        updateUserSkill.setSkillLevel(8);
        updateUserSkill.setSkillDetail("test");

        // 更新実行
        int updatedCount = userSkillRepository.update(updateUserSkill);
        // 検証：1件の更新に成功していること
        Assertions.assertEquals(1, updatedCount);
    }
}
