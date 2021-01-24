package com.example.myApp.dbunit.domain.service;

import com.example.myApp.dao.UserRepository;
import com.example.myApp.dbunit.dataset.CsvDataSetLoader;
import com.example.myApp.model.User;
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
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private final String userDate = "2020-12-06 10:10:59";
    private final  SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Test
    @DatabaseSetup("/testdata/userServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void findAll() {
        // 検索結果
        List<User> users = userRepository.selectAll();
        Assertions.assertEquals(4, users.size());
    }

    @Test
    @DatabaseSetup("/testdata/userServiceTest/init-data") // テスト実行前に初期データを投入
    @ExpectedDatabase(value = "/testdata/userServiceTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
    void findByPk() {
        // id=2のデータの期待値
        User expect = new User();
        expect.setUserName("yukimatsumori");
        expect.setFirstName("yuki");
        expect.setLastName("matsumori");
        expect.setEmail("yuki.matsumori@codeberry.co.jp");
        expect.setPassword("password1!");
        expect.setPhone("08012345678");
        expect.setImageUrl("foo.jpg");
        try {
        expect.setCreatedAt(sdFormat.parse(userDate));
        expect.setUpdatedAt(sdFormat.parse(userDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // id=2の検索結果
        User actual = userRepository.select(2);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DatabaseSetup("/testdata/userServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        User newUser = new User();
        newUser.setUserName("masa");
        newUser.setFirstName("masakado");
        newUser.setLastName("taira");
        newUser.setEmail("forever.heike@gmail.com");
        newUser.setPassword("password1!");
        newUser.setPhone("11850425");
        newUser.setImageUrl("kogarasumaru.jpg");

         //登録実行
        int actual = userRepository.insert(newUser);
         //検証：1件の追加に成功していること
        Assertions.assertEquals(1, actual);
    }

    @Test
    @DatabaseSetup("/testdata/userServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userServiceTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが更新されていること
    void update() {
        // 更新するデータを準備
        User updateUser = new User();
        updateUser.setId(1);
        updateUser.setUserName("Charly");
        updateUser.setFirstName("Charles");
        updateUser.setLastName("Darwin");
        updateUser.setEmail("Charly@hotmail.co.jp");
        updateUser.setPassword("password1!");
        updateUser.setPhone("1234567890");
        updateUser.setImageUrl("evolution.jpg");

        // 更新実行
        int updatedCount = userRepository.update(updateUser);
        // 検証：1件の更新に成功していること
        Assertions.assertEquals(1, updatedCount);
    }

    @Test
    @DatabaseSetup("/testdata/userServiceTest/init-data")
    @ExpectedDatabase(value = "/testdata/userServiceTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが削除されていること
    void delete() {
        // id=1のレコードを削除
        int deletedCount = userRepository.delete(4);
        // 検証：1件の削除に成功していること
        Assertions.assertEquals(1, deletedCount);
    }
}
