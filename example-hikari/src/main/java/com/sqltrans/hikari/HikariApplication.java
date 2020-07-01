package com.sqltrans.hikari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class HikariApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HikariApplication.class, args);
    }

    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")//把同类的配置信息自动封装成实体类
    @Primary//自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")//把同类的配置信息自动封装成实体类
    public DataSource testDataSource2() {
        return DataSourceBuilder.create().build();
    }



}