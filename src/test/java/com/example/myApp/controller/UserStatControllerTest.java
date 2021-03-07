package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.UserStat;
import com.example.myApp.model.UserStats;
import com.example.myApp.service.UserStatService;
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
public class UserStatControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private UserStatService userStatMapper;
    @InjectMocks
    private UserStatController userStatController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userStatController)
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

    //selectAllStatusByUserのテスト
    @Test
    public void selectAllStatusByUserTest() throws Exception {
        // モックデータ（兼、期待値データ
        UserStat userStat1 = new UserStat();
        userStat1.setUser_id(1);
        userStat1.setStatus_id(1);
        userStat1.setStatusName("namae1");
        userStat1.setStatusVolume(20);

        UserStat userStat2 = new UserStat();
        userStat2.setUser_id(1);
        userStat2.setStatus_id(2);
        userStat2.setStatusName("namae2");
        userStat2.setStatusVolume(30);

        List<UserStat> list = new ArrayList<UserStat>(Arrays.asList(userStat1, userStat2));

        // Daoの戻り値をモック
        when(userStatMapper.selectAllStatusByUser(userStat1.getStatus_id())).thenReturn(list);

        // 返却結果の期待値生成
        UserStats expectObject = new UserStats();
        expectObject.setUserStatList(list);

        // エンドポイントのテスト実施
        MvcResult result =
                mockMvc.perform(get("/api/users/{user_id}/userStats",1))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // daoの利用状況を検証
        verify(userStatMapper, times(1)).selectAllStatusByUser(userStat1.getStatus_id());
        verifyNoMoreInteractions(userStatMapper);
        // 実行結果のレスポンスからオブジェクト生成
        ObjectMapper mapper = new ObjectMapper();
        UserStats actualObject = mapper.readValue(result.getResponse().getContentAsString(), UserStats.class);
        // 値の検証
        assertThat(actualObject, is(expectObject));
    }

    //新規投稿のテスト
    @Test
    public void createTest() throws Exception {
        UserStat userStat = new UserStat();
        userStat.setUser_id(1);
        userStat.setStatusName("Time");
        userStat.setStatusVolume(10);
        when(userStatMapper.insert(userStat)).thenReturn(1);
        mockMvc.perform(
                post("/api/users/{user_id}/userStats",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userStat)))
                .andExpect(status().isOk());
        verify(userStatMapper, times(1)).insert(userStat);
        verifyNoMoreInteractions(userStatMapper);
    }

    //ステータス更新のテスト
    @Test
    public void updateTest() throws Exception {
        UserStat userStat = new UserStat();
        userStat.setStatus_id(1);
        userStat.setStatusName("WebSiteMade");
        userStat.setStatusVolume(10);
        when(userStatMapper.update(userStat)).thenReturn(1);
        mockMvc.perform(
                put("/api/users/userStats/{status_id}", userStat.getStatus_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userStat)))
                .andExpect(status().isOk());
        verify(userStatMapper, times(1)).update(userStat);
        verifyNoMoreInteractions(userStatMapper);
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
