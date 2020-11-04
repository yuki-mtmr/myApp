package com.example.myApp.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableSwagger2
@MapperScan("com.example.myApp.dao")
public class SqlMappingConfig {

    @Bean
    DataSourceInitializer dataSourceInitializer(DataSource ds,
                                                @Value("classpath:/schema.sql") Resource schema,
                                                @Value("classpath:/data.sql") Resource data) {
        DataSourceInitializer init = new DataSourceInitializer();
        init.setDatabasePopulator(new ResourceDatabasePopulator(schema, data));
        init.setDataSource(ds);
        return init;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        ResourcePatternResolver resolver =
                ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        factory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        factory.setMapperLocations(resolver.getResources("classpath:dao/**/*.xml"));
        return factory;
    }
}