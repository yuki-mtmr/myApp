package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.dao.UserDao;
import com.example.myApp.filter.LogFilter;
import com.example.myApp.model.CreateUsersRequest;
import com.example.myApp.model.User;
import com.example.myApp.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
public class UserControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private UserDao userMapper;

    @Autowired
    LogFilter logFilter;

    @InjectMocks
    private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new CustomRestExceptionHandler())
                .build();
    }

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    //一覧表示のテスト

    @Test
    public void selectAllTest() throws Exception {


        // モックデータ（兼、期待値データ
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("test1");
        user1.setFirstName("namae1");
        user1.setLastName("myouji1");
        user1.setEmail("test1@test.com");
        user1.setPassword("password1");
        user1.setPhone("07011112222");
        user1.setImageUrl("gazou1");

        User user2 = new User();
        user2.setId(2);
        user2.setUserName("test2");
        user2.setFirstName("namae2");
        user2.setLastName("myouji2");
        user2.setEmail("test2@test.com");
        user2.setPassword("password2");
        user2.setPhone("07022223333");
        user2.setImageUrl("gazou2");

        List<User> list = new ArrayList<User>(Arrays.asList(user1, user2));

        // Daoの戻り値をモック
        when(userMapper.selectAll()).thenReturn(list);

        // 返却結果の期待値生成
        Users expectObject = new Users();
        expectObject.setUserList(list);

        // エンドポイントのテスト実施
        MvcResult result =
                mockMvc.perform(get("/api/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // daoの利用状況を検証
        verify(userMapper, times(1)).selectAll();
        verifyNoMoreInteractions(userMapper);
        // 実行結果のレスポンスからオブジェクト生成
        ObjectMapper mapper = new ObjectMapper();
        Users actualObject = mapper.readValue(result.getResponse().getContentAsString(), Users.class);
        // 値の検証
        assertThat(actualObject, is(expectObject));

    }

    //1件選択のテスト

    @Test
    public void selectTest() throws Exception {
        User expectObject = new User();
        expectObject.setId(1);
        expectObject.setUserName("selectTest1");
        when(userMapper.select(1)).thenReturn(expectObject);
        MvcResult result =
                mockMvc.perform(get("/api/users/{id}", 1))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();

        verify(userMapper, times(1)).select(1);
        verifyNoMoreInteractions(userMapper);

        ObjectMapper mapper = new ObjectMapper();
        User actualObject = mapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(actualObject, is(expectObject));
    }

    //新規投稿のテスト

    @Test
    public void createTest() throws Exception {
        User user = new User();
        user.setUserName("test3");
        user.setFirstName("namae3");
        user.setLastName("myouji3");
        user.setEmail("test3@test.com");
        user.setPassword("password3");
        user.setPhone("07011113333");
        user.setImageUrl("gazou3");
        doNothing().when(userMapper).insert(user);
        mockMvc.perform(
                post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(asJsonString(user)))
                .andExpect(status().isOk());
        verify(userMapper, times(1)).insert(user);
        verifyNoMoreInteractions(userMapper);
    }

    //更新のテスト

    @Test
    public void updateTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("test4");
        user.setFirstName("namae4");
        user.setLastName("myouji4");
        user.setEmail("test4@test.com");
        user.setPassword("password4");
        user.setPhone("07011114444");
        user.setImageUrl("gazou4");
        when(userMapper.update(user)).thenReturn(1);
        mockMvc.perform(
                put("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk());
        verify(userMapper, times(1)).update(user);
        verifyNoMoreInteractions(userMapper);
    }

    //削除のテスト

    @Test
    public void deleteTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("test5");
        when(userMapper.delete(user.getId())).thenReturn(1);
        mockMvc.perform(
                delete("/api/users/{id}", user.getId()))
                .andExpect(status().isOk());
        verify(userMapper, times(1)).delete(user.getId());
        verifyNoMoreInteractions(userMapper);
    }

    /*
     * javaオブジェクトをjsonに変換
     */

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //ここから異常系のテスト

    //例外
    @Test
    public void 存在しないレコードにアクセス() throws Exception {

        int id = 999;
        MvcResult result =
                mockMvc.perform(get("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                        .andExpect(jsonPath("$.message").value("Invalid users_id : 999"))
                        .andReturn();
    }

    //putでusersにアクセス
    @Test
    public void PUTでアクセスする() throws Exception {

        // PUTで「/api/users」にアクセスする
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.put("/api/users"))
                        // レスポンスのステータスコードが405（METHOD_NOT_ALLOWED）であることを検証する
                        .andExpect(status().isMethodNotAllowed())
                        .andExpect(jsonPath("$.status").value("METHOD_NOT_ALLOWED"))
                        .andExpect(jsonPath("$.message").value("Request method 'PUT' not supported"))
                        .andReturn();
    }

    //Validation
    @Test
    public void validation通過テスト() {
        CreateUsersRequest user = new CreateUsersRequest();
        user.setUserName("test6");
        user.setFirstName("namae6");
        user.setLastName("myouji6");
        user.setEmail("test6@test.com");
        user.setPassword("password6");
        user.setPhone("07011116666");
        user.setImageUrl("gazou6");

        //when:
        Set<ConstraintViolation<CreateUsersRequest>> violations
                = validator.validate(user);

        //then:
        assertTrue(violations.isEmpty());
    }


    @Test
    public void userName文字制限テスト() {
        CreateUsersRequest user = new CreateUsersRequest();
        user.setUserName("test7aaaaaaaaaaaaaaaaaaaa");
        user.setFirstName("name7");
        user.setLastName("myouji7");
        user.setEmail("aaa@sss.com");
        user.setPassword("ddddddddddddd");
        user.setPhone("09011112222");
        user.setImageUrl("gazou8");

        //when:
        Set<ConstraintViolation<CreateUsersRequest>> violations
                = validator.validate(user);

        //then:
        assertEquals(violations.size(), 1);

        ConstraintViolation<CreateUsersRequest> violation
                = violations.iterator().next();
        assertEquals("must be within 20 characters",
                violation.getMessage());
        assertEquals("userName", violation.getPropertyPath().toString());
        assertEquals("test7aaaaaaaaaaaaaaaaaaaa", violation.getInvalidValue());
    }

    @Test
    public void password文字数制限テスト() {
        CreateUsersRequest user = new CreateUsersRequest();
        user.setUserName("test8");
        user.setFirstName("name8");
        user.setLastName("myouji8");
        user.setEmail("aaa@sss.com");
        user.setPassword("passwoooooooooooooord");
        user.setPhone("09011112222");
        user.setImageUrl("gazou8");

        //when:
        Set<ConstraintViolation<CreateUsersRequest>> violations
                = validator.validate(user);

        //then:
        assertEquals(violations.size(), 1);

        ConstraintViolation<CreateUsersRequest> violation
                = violations.iterator().next();
        assertEquals("must be 1 to 20 characters",
                violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals("passwoooooooooooooord", violation.getInvalidValue());
    }

    @Test
    public void firstName文字数制限テスト() {
        CreateUsersRequest user = new CreateUsersRequest();
        user.setUserName("test9");
        user.setFirstName("namaebbbbbbbbbbbbbbbbbbbbb");
        user.setLastName("myouji9");
        user.setEmail("aaa@sss.com");
        user.setPassword("password");
        user.setPhone("09011112222");
        user.setImageUrl("gazou8");

        //when:
        Set<ConstraintViolation<CreateUsersRequest>> violations
                = validator.validate(user);

        //then:
        assertEquals(violations.size(), 1);

        ConstraintViolation<CreateUsersRequest> violation
                = violations.iterator().next();
        assertEquals("must be within 20 characters",
                violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
        assertEquals("namaebbbbbbbbbbbbbbbbbbbbb", violation.getInvalidValue());
    }

}
