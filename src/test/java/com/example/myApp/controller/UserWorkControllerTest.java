package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.UserWork;
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
