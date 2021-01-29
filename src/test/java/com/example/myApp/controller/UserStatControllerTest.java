package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.UserStat;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    //新規投稿のテスト
    @Test
    public void createTest() throws Exception {
        UserStat userStat = new UserStat();
        userStat.setUser_id(1);
        userStat.setStatusName("Time");
        userStat.setStatusVolume(10);
        when(userStatMapper.insert(userStat)).thenReturn(1);
        mockMvc.perform(
                post("/api/users/{id}/userStats",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userStat)))
                .andExpect(status().isOk());
        verify(userStatMapper, times(1)).insert(userStat);
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
