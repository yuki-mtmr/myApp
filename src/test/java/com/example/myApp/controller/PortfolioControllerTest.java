package com.example.myApp.controller;

import com.example.myApp.ErrorHandler.CustomRestExceptionHandler;
import com.example.myApp.model.*;
import com.example.myApp.service.PortfolioService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@EnableWebMvc
@Slf4j
public class PortfolioControllerTest {

    private MockMvc mockMvc;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Mock
    private PortfolioService portfolioMapper;
    @InjectMocks
    private PortfolioController portfolioController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(portfolioController)
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
        Portfolio portfolio1 = new Portfolio();
        portfolio1.setPortfolio_id(1);
        portfolio1.setUser_id(1);
        portfolio1.setPortfolioName("test1");
        portfolio1.setPortfolioPic("pic1");
        portfolio1.setIntroduction("test1");

        Portfolio portfolio2 = new Portfolio();
        portfolio2.setPortfolio_id(2);
        portfolio2.setUser_id(2);
        portfolio2.setPortfolioName("test2");
        portfolio2.setPortfolioPic("pic2");
        portfolio2.setIntroduction("test2");

        List<Portfolio> list = new ArrayList<Portfolio>(Arrays.asList(portfolio1, portfolio2));

        // Daoの戻り値をモック
        when(portfolioMapper.selectAll()).thenReturn(list);

        // 返却結果の期待値生成
        Portfolios expectObject = new Portfolios();
        expectObject.setPortfolioList(list);

        // エンドポイントのテスト実施
        MvcResult result =
                mockMvc.perform(get("/api/portfolios"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        // daoの利用状況を検証
        verify(portfolioMapper, times(1)).selectAll();
        verifyNoMoreInteractions(portfolioMapper);
        // 実行結果のレスポンスからオブジェクト生成
        ObjectMapper mapper = new ObjectMapper();
        Portfolios actualObject = mapper.readValue(result.getResponse().getContentAsString(), Portfolios.class);
        // 値の検証
        assertThat(actualObject, is(expectObject));
    }
}
