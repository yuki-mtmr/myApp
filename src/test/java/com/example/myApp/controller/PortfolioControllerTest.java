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

    //1件選択のテスト
    @Test
    public void selectTest() throws Exception {
        Portfolio expectObject = new Portfolio();
        expectObject.setUser_id(1);
        expectObject.setPortfolioName("test");
        expectObject.setPortfolioPic("pic1");
        expectObject.setIntroduction("test");
        when(portfolioMapper.select(1)).thenReturn(expectObject);
        MvcResult result =
                mockMvc.perform(get("/api/portfolios/{portfolio_id}", 1))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andReturn();

        verify(portfolioMapper, times(1)).select(1);
        verifyNoMoreInteractions(portfolioMapper);

        ObjectMapper mapper = new ObjectMapper();
        Portfolio actualObject = mapper.readValue(result.getResponse().getContentAsString(), Portfolio.class);
        assertThat(actualObject, is(expectObject));
    }

    //新規投稿のテスト
    @Test
    public void createTest() throws Exception {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser_id(1);
        portfolio.setPortfolioName("test");
        portfolio.setPortfolioPic("pic1");
        portfolio.setIntroduction("test");
        when(portfolioMapper.insert(portfolio)).thenReturn(1);
        mockMvc.perform(
                post("/api/users/{user_id}/portfolios",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(portfolio)))
                .andExpect(status().isOk());
        verify(portfolioMapper, times(1)).insert(portfolio);
        verifyNoMoreInteractions(portfolioMapper);
    }

    //ポートフォリオ更新のテスト
    @Test
    public void updateTest() throws Exception {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolio_id(2);
        portfolio.setPortfolioName("updateTest");
        portfolio.setPortfolioPic("updateTestPic");
        portfolio.setIntroduction("updateIntroduction");
        when(portfolioMapper.update(portfolio)).thenReturn(1);
        mockMvc.perform(
                put("/api/users/portfolios/{portfolio_id}", portfolio.getPortfolio_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(portfolio)))
                .andExpect(status().isOk());
        verify(portfolioMapper, times(1)).update(portfolio);
        verifyNoMoreInteractions(portfolioMapper);
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
