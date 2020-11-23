package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.RecordNotFoundException;
import com.example.myApp.dao.UserDao;
import com.example.myApp.model.CreateUsersRequest;
import com.example.myApp.model.User;
import com.example.myApp.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private UserDao userMapper;

    @InjectMocks
    private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
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
        user1.setUserName("test1");
        user1.setFirstName("namae1");
        user1.setLastName("myouji1");
        user1.setEmail("test1@test.com");
        user1.setPassword("password1");
        user1.setPhone("07011112222");
        user1.setImageUrl("gazou1");

        User user2 = new User();
        user2.setUserName("test2");
        user2.setFirstName("namae2");
        user2.setLastName("myouji2");
        user2.setEmail("test2@test.com");
        user2.setPassword("password2");
        user2.setPhone("07022223333");
        user2.setImageUrl("gazou2");

        List<User> list = new ArrayList<User>();
        list.addAll(Arrays.asList(user1, user2));

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

//    @Test
//    public void selectTest() throws Exception {
//        User expectObject = new User();
//        expectObject.setId(1);
//        expectObject.setUserName("selectTest1");
//        when(userMapper.select(1)).thenReturn(expectObject);
//        MvcResult result =
//                mockMvc.perform(get("/api/users/{id}", 1))
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                        .andReturn();
//
//        verify(userMapper, times(1)).select(1);
//        verifyNoMoreInteractions(userMapper);
//
//        ObjectMapper mapper = new ObjectMapper();
//        User actualObject = mapper.readValue(result.getResponse().getContentAsString(), User.class);
//        assertThat(actualObject, is(expectObject));
//    }
//
//    //新規投稿のテスト
//
//    @Test
//    public void createTest() throws Exception {
//        User user = new User();
//        user.setUserName("test3");
//        user.setFirstName("namae3");
//        user.setLastName("myouji3");
//        user.setEmail("test3@test.com");
//        user.setPassword("password3");
//        user.setPhone("07011113333");
//        user.setImageUrl("gazou3");
//        doNothing().when(userMapper).insert(user);
//        mockMvc.perform(
//                post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                        .content(asJsonString(user)))
//                .andExpect(status().isOk());
//        verify(userMapper, times(1)).insert(user);
//        verifyNoMoreInteractions(userMapper);
//    }
//
//    //更新のテスト
//
//    @Test
//    public void updateTest() throws Exception {
//        User user = new User();
//        user.setUserName("test4");
//        user.setFirstName("namae4");
//        user.setLastName("myouji4");
//        user.setEmail("test4@test.com");
//        user.setPassword("password4");
//        user.setPhone("07011114444");
//        user.setImageUrl("gazou4");
//        when(userMapper.update(user)).thenReturn(1);
//        mockMvc.perform(
//                put("/api/users/{id}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(user)))
//                .andExpect(status().isOk());
//        verify(userMapper, times(1)).update(user);
//        verifyNoMoreInteractions(userMapper);
//    }
//
//    //削除のテスト
//
//    @Test
//    public void deleteTest() throws Exception {
//        User user = new User();
//        user.setId(1);
//        user.setUserName("test5");
//        when(userMapper.delete(user.getId())).thenReturn(1);
//        mockMvc.perform(
//                delete("/api/users/{id}", user.getId()))
//                .andExpect(status().isOk());
//        verify(userMapper, times(1)).delete(user.getId());
//        verifyNoMoreInteractions(userMapper);
//    }
//
//    /*
//     * javaオブジェクトをjsonに変換
//     */
//
//    public static String asJsonString(final Object obj) {
//        try {
//            final ObjectMapper mapper = new ObjectMapper();
//            final String jsonContent = mapper.writeValueAsString(obj);
//            return jsonContent;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    //ここから異常系のテスト
//
//    //例外
//    @Test
//    public void 存在しないレコードにアクセス() throws Exception {
//
//        int id = 999;
//        MvcResult result =
//                mockMvc.perform(get("/api/users/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isNotFound())
//                        .andReturn();
//
//        Exception exception = result.getResolvedException();
//        assertThat(exception, is(instanceOf(RecordNotFoundException.class)));
//        assertThat(exception.getMessage(), is("Invalid users id : " + id));
//    }
//
//    //putでusersにアクセス
//    @Test
//    public void PUTでアクセスする() throws Exception {
//
//        // PUTで「/api/users」にアクセスする
//        MvcResult result =
//                mockMvc.perform(MockMvcRequestBuilders.put("/api/users"))
//                        // レスポンスのステータスコードが405（METHOD_NOT_ALLOWED）であることを検証する
//                        .andExpect(status().isMethodNotAllowed())
//                        .andReturn();
//
//        Exception exception = result.getResolvedException();
//        assertThat(exception, is(instanceOf(HttpRequestMethodNotSupportedException.class)));
//        assertThat(exception.getMessage(), is("Request method 'PUT' not supported"));
//    }
//
//    //Validation
//    @Test
//    public void validation通過テスト() {
//        //given:
//        CreateUsersRequest user = new CreateUsersRequest();
//        user.setUserName("test6");
//        user.setFirstName("namae6");
//        user.setLastName("myouji6");
//        user.setEmail("test6@test.com");
//        user.setPassword("password6");
//        user.setPhone("07011116666");
//        user.setImageUrl("gazou6");
//
//        //when:
//        Set<ConstraintViolation<CreateUsersRequest>> violations
//                = validator.validate(user);
//
//        //then:
//        assertTrue(violations.isEmpty());
//    }
//
//
//    @Test
//    public void 空テスト() {
//        //must not be empty:
//        CreateUsersRequest user = new CreateUsersRequest();
//        user.setUserName("");
//        user.setFirstName("");
//        user.setLastName("");
//        user.setEmail("");
//        user.setPassword("");
//        user.setPhone("");
//        user.setImageUrl("");
//
//        //when:
//        Set<ConstraintViolation<CreateUsersRequest>> violations
//                = validator.validate(user);
//
//        //then:
//        assertEquals(violations.size(), 7);
//
//        ConstraintViolation<CreateUsersRequest> violation
//                = violations.iterator().next();
//        assertEquals("must not be empty",
//                violation.getMessage());
//        assertEquals("userName", violation.getPropertyPath().toString());
//        assertEquals("", violation.getInvalidValue());
//    }
//
//    @Test
//    public void title文字制限テスト() {
//        //given too short name:
//        CreateUsersRequest user = new CreateUsersRequest();
//        user.setUserName("aaaaaaaaaaaaaaaaaaaa");
//        user.setFirstName("bbbbbbbbbbbbbbbbbbbbb");
//        user.setLastName("ccccccccccccccccccccc");
//        user.setEmail("aaa@sss.com");
//        user.setPassword("ddddddddddddddddddddd");
//        user.setPhone("090-11112222");
//        user.setImageUrl("gazou8");
//
//        //when:
//        Set<ConstraintViolation<CreateUsersRequest>> violations
//                = validator.validate(user);
//
//        //then:
//        assertEquals(violations.size(), 1);
//
//        ConstraintViolation<CreateUsersRequest> violation
//                = violations.iterator().next();
//        assertEquals("must be 1 to 30 characters",
//                violation.getMessage());
//        assertEquals("title", violation.getPropertyPath().toString());
//        assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", violation.getInvalidValue());
//    }
//
//    @Test
//    public void price文字列規制テスト() {
//        //given too short name:
//        CreateUsersRequest user = new CreateUsersRequest();
//        user.setUserName("aaaaaaaaaaaaaaaaaaaa");
//        user.setFirstName("bbbbbbbbbbbbbbbbbbbbb");
//        user.setLastName("ccccccccccccccccccccc");
//        user.setEmail("aaa@sss.com");
//        user.setPassword("ddddddddddddddddddddd");
//        user.setPhone("090-11112222");
//        user.setImageUrl("gazou8");
//
//        //when:
//        Set<ConstraintViolation<CreateUsersRequest>> violations
//                = validator.validate(user);
//
//        //then:
//        assertEquals(violations.size(), 1);
//
//        ConstraintViolation<CreateUsersRequest> violation
//                = violations.iterator().next();
//        assertEquals("is not proper number",
//                violation.getMessage());
//        assertEquals("price", violation.getPropertyPath().toString());
//        assertEquals("aaa", violation.getInvalidValue());
//    }
//
//    @Test
//    public void price桁規制テスト() {
//        //given too short name:
//        CreateUsersRequest user = new CreateUsersRequest();
//        user.setUserName("aaaaaaaaaaaaaaaaaaaa");
//        user.setFirstName("bbbbbbbbbbbbbbbbbbbbb");
//        user.setLastName("ccccccccccccccccccccc");
//        user.setEmail("aaa@sss.com");
//        user.setPassword("ddddddddddddddddddddd");
//        user.setPhone("090-11112222");
//        user.setImageUrl("gazou8");
//
//        //when:
//        Set<ConstraintViolation<CreateUsersRequest>> violations
//                = validator.validate(user);
//
//        //then:
//        assertEquals(violations.size(), 1);
//
//        ConstraintViolation<CreateUsersRequest> violation
//                = violations.iterator().next();
//        assertEquals("is not proper number",
//                violation.getMessage());
//        assertEquals("price", violation.getPropertyPath().toString());
//        assertEquals("99999999999", violation.getInvalidValue());
//    }

}
