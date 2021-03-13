package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.*;
import com.example.myApp.service.UserSkillService;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
@Slf4j
public class UserSkillControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private UserSkillService userSkillMapper;
    @InjectMocks
    private UserSkillController userSkillController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userSkillController)
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
    public void selectAllSkillByUserTest() throws Exception {

        // モックデータ（兼、期待値データ
        UserSkill userSkill1 = new UserSkill();
        userSkill1.setUser_id(1);
        userSkill1.setSkill_id(1);
        userSkill1.setSkillName("namae1");
        userSkill1.setSkillLevel(20);
        userSkill1.setSkillDetail("test");

        UserSkill userSkill2 = new UserSkill();
        userSkill2.setUser_id(1);
        userSkill2.setSkill_id(2);
        userSkill2.setSkillName("namae2");
        userSkill2.setSkillLevel(30);
        userSkill2.setSkillDetail("test");

        List<UserSkill> list = new ArrayList<UserSkill>(Arrays.asList(userSkill1, userSkill2));

        // Daoの戻り値をモック
        when(userSkillMapper.selectAllSkillByUser(userSkill1.getSkill_id())).thenReturn(list);

        // 返却結果の期待値生成
        UserSkills expectObject = new UserSkills();
        expectObject.setUserSkillList(list);

        // エンドポイントのテスト実施
        MvcResult result =
                mockMvc.perform(get("/api/users/{user_id}/userSkills",1))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // daoの利用状況を検証
        verify(userSkillMapper, times(1)).selectAllSkillByUser(userSkill1.getSkill_id());
        verifyNoMoreInteractions(userSkillMapper);
        // 実行結果のレスポンスからオブジェクト生成
        ObjectMapper mapper = new ObjectMapper();
        UserSkills actualObject = mapper.readValue(result.getResponse().getContentAsString(), UserSkills.class);
        // 値の検証
        assertThat(actualObject, is(expectObject));
    }

    //新規投稿のテスト
    @Test
    public void createTest() throws Exception {
        UserSkill userSkill = new UserSkill();
        userSkill.setUser_id(1);
        userSkill.setSkillName("TypeScript");
        userSkill.setSkillLevel(10);
        userSkill.setSkillDetail("test");
        when(userSkillMapper.insert(userSkill)).thenReturn(1);
        mockMvc.perform(
                post("/api/users/{user_id}/userSkills",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSkill)))
                .andExpect(status().isOk());
        verify(userSkillMapper, times(1)).insert(userSkill);
        verifyNoMoreInteractions(userSkillMapper);
    }

    //スキル更新のテスト
    @Test
    public void updateTest() throws Exception {
        UserSkill userSkill = new UserSkill();
        userSkill.setSkill_id(1);
        userSkill.setSkillName("TypeScript");
        userSkill.setSkillLevel(10);
        userSkill.setSkillDetail("test");
        when(userSkillMapper.update(userSkill)).thenReturn(1);
        mockMvc.perform(
                put("/api/users/userSkills/{skill_id}", userSkill.getSkill_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSkill)))
                .andExpect(status().isOk());
        verify(userSkillMapper, times(1)).update(userSkill);
        verifyNoMoreInteractions(userSkillMapper);
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
