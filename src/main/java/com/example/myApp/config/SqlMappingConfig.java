package com.example.myApp.config;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@MapperScan("com.example.myApp.dao")
public class SqlMappingConfig {

}