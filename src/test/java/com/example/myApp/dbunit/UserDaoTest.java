package com.example.myApp.dbunit;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.controller.UserController;
import com.example.myApp.dao.UserDao;
import com.example.myApp.model.User;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import java.util.List;

@Slf4j
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。＊CsvDataSetLoaderクラスは自作します（後述）
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional // @DatabaseSetupで投入するデータをテスト処理と同じトランザクション制御とする。（テスト後に投入データもロールバックできるように）
class UserDaoTest {

    @Autowired
    private UserDao userDao;


    @Test
    @DatabaseSetup("/testdata/userDaoTest/init-data") // テスト実行前に初期データを投入
    @ExpectedDatabase(value = "/testdata/userDaoTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後のデータ検証（初期データのままであること）
    void findByPk() {
        // id=3のデータの期待値
        User expect = new User();
        expect.setId(1);
        expect.setUserName("test1");
        expect.setFirstName("namae1");
        expect.setLastName("myouji1");
        expect.setEmail("test1@test.com");
        expect.setPassword("password1");
        expect.setPhone("07011112222");
        expect.setImageUrl("gazou1");
        // id=3の検索結果
        User actual = userDao.select(3);
        // 検証：期待値と一致していること
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DatabaseSetup("/testdata/userDaoTest/init-data")
    @ExpectedDatabase(value = "/testdata/userDaoTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void findAll() {
        // 検索結果
        List<User> users = userDao.selectAll();
        Assertions.assertEquals(4, users.size());
    }

    @Test
    @DatabaseSetup("/testdata/userDaoTest/init-data")
    @ExpectedDatabase(value = "/testdata/userDaoTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に１件データが追加されていること
    void create() {
        // 登録するデータを準備
        User newUser = new User();
        newUser.setId(1);
        newUser.setUserName("test1");
        newUser.setFirstName("namae1");
        newUser.setLastName("myouji1");
        newUser.setEmail("test1@test.com");
        newUser.setPassword("password1");
        newUser.setPhone("07011112222");
        newUser.setImageUrl("gazou1");
        // 登録実行
        int createdCount = userDao.insert(newUser);
        // 検証：1件の追加に成功していること
        Assertions.assertEquals(1, createdCount);
        // 検証：登録に使ったオブジェクトに採番されたid=5が設定されていること
        Assertions.assertEquals(5, newUser.getId());
    }

    @Test
    @DatabaseSetup("/testdata/userDaoTest/init-data")
    @ExpectedDatabase(value = "/testdata/userDaoTest/after-update-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが更新されていること
    void update() {
        // 更新するデータを準備
        User updateUser = new User();
        updateUser.setId(1);
        updateUser.setUserName("test1");
        updateUser.setFirstName("namae1");
        updateUser.setLastName("myouji1");
        updateUser.setEmail("test1@test.com");
        updateUser.setPassword("password1");
        updateUser.setPhone("07011112222");
        updateUser.setImageUrl("gazou1");
        // 更新実行
        int updatedCount = userDao.update(updateUser);
        // 検証：1件の更新に成功していること
        Assertions.assertEquals(1, updatedCount);
    }

    @Test
    @DatabaseSetup("/testdata/userDaoTest/init-data")
    @ExpectedDatabase(value = "/testdata/userDaoTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED) // テスト実行後に1件データが削除されていること
    void delete() {
        // id=1のレコードを削除
        int deletedCount = userDao.delete(1);
        // 検証：1件の削除に成功していること
        Assertions.assertEquals(1, deletedCount);
    }
}
