package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.UserWork;
import com.example.myApp.model.UserWorks;
import com.example.myApp.service.UserWorkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
@Slf4j
public class UserWorkControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private UserWorkService userWorkMapper;
    @InjectMocks
    private UserWorkController userWorkController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userWorkController)
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
    public void selectAllWorkByUserTest() throws Exception {

        // モックデータ（兼、期待値データ
        UserWork userWork1 = new UserWork();
        userWork1.setUser_id(1);
        userWork1.setWork_id(1);
        userWork1.setWorkThumbnail("pic2");
        userWork1.setWorkLink("test1");
        userWork1.setWorkDetail("test1");

        UserWork userWork2 = new UserWork();
        userWork2.setUser_id(1);
        userWork2.setWork_id(2);
        userWork2.setWorkThumbnail("pic2");
        userWork2.setWorkLink("test2");
        userWork2.setWorkDetail("test2");

        List<UserWork> list = new ArrayList<UserWork>(Arrays.asList(userWork1, userWork2));

        // Daoの戻り値をモック
        when(userWorkMapper.selectAllWorkByUser(userWork1.getWork_id())).thenReturn(list);

        // 返却結果の期待値生成
        UserWorks expectObject = new UserWorks();
        expectObject.setUserWorkList(list);

        // エンドポイントのテスト実施
        MvcResult result =
                mockMvc.perform(get("/api/users/{id}/userWorks",1))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // daoの利用状況を検証
        verify(userWorkMapper, times(1)).selectAllWorkByUser(userWork1.getWork_id());
        verifyNoMoreInteractions(userWorkMapper);
        // 実行結果のレスポンスからオブジェクト生成
        ObjectMapper mapper = new ObjectMapper();
        UserWorks actualObject = mapper.readValue(result.getResponse().getContentAsString(), UserWorks.class);
        // 値の検証
        assertThat(actualObject, is(expectObject));
    }

    //新規投稿のテスト
    @Test
    public void createTest() throws Exception {
        UserWork userWork = new UserWork();
        userWork.setUser_id(1);
        userWork.setWorkThumbnail("pic");
        userWork.setWorkLink("test");
        userWork.setWorkDetail("test");
        when(userWorkMapper.insert(userWork)).thenReturn(1);
        mockMvc.perform(
                post("/api/users/{id}/userWorks",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userWork)))
                .andExpect(status().isOk());
        verify(userWorkMapper, times(1)).insert(userWork);
        verifyNoMoreInteractions(userWorkMapper);
    }

    //ワーク更新のテスト
    @Test
    public void updateTest() throws Exception {
        UserWork userWork = new UserWork();
        userWork.setWork_id(1);
        userWork.setWorkThumbnail("pic");
        userWork.setWorkLink("test");
        userWork.setWorkDetail("test");
        when(userWorkMapper.update(userWork)).thenReturn(1);
        mockMvc.perform(
                put("/api/users/userWorks/{work_id}", userWork.getWork_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userWork)))
                .andExpect(status().isOk());
        verify(userWorkMapper, times(1)).update(userWork);
        verifyNoMoreInteractions(userWorkMapper);
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
}
