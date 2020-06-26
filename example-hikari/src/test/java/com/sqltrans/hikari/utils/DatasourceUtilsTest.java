package com.sqltrans.hikari.utils;

import com.sqltrans.hikari.HikariApplication;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HikariApplication.class})// 指定启动类
@Slf4j
public class DatasourceUtilsTest {


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mysql");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        BeanRegisterUtils.registerBean("HikariDataSource", HikariDataSource.class, config);

        Object test = SpringBeanTools.getBean("HikariDataSource");
        System.out.println(test);


        HikariConfig config1 = new HikariConfig();
        config1.setUsername("root");
        config1.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mall");
        config1.setPassword("root");
        config1.setDriverClassName("com.mysql.jdbc.Driver");
        config1.setPoolName("hikari2");
        config1.setPoolName("test2");
        BeanRegisterUtils.registerBean("HikariDataSource2",HikariDataSource.class, config1);

        Object test1 = SpringBeanTools.getBean("HikariDataSource2");
        System.out.println(test1);


    }

    @Test
    public void getConnection() {
    }
}