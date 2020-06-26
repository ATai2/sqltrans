package com.sqltrans.hikari.controller;

import com.sqltrans.hikari.utils.BeanRegisterUtils;
import com.sqltrans.hikari.utils.SpringBeanTools;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class DSController {


    @Autowired
    private DataSource dataSource;

    @GetMapping("/ds")
    public String addDataSource(){

        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mysql");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        BeanRegisterUtils.registerBean("HikariDataSource", HikariDataSource.class, config);

        HikariDataSource test = (HikariDataSource) SpringBeanTools.getBean("HikariDataSource");
        System.out.println(test);


        HikariConfig config1 = new HikariConfig();
        config1.setUsername("root");
        config1.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mall");
        config1.setPassword("root");
        config1.setDriverClassName("com.mysql.jdbc.Driver");
        config1.setPoolName("hikari2");
        config1.setPoolName("test2");
        BeanRegisterUtils.registerBean("HikariDataSource2",HikariDataSource.class, config1);

        HikariDataSource test1 = (HikariDataSource) SpringBeanTools.getBean("HikariDataSource2");
        System.out.println(test1);

        excute(dataSource);
        excute(test);
        excute(test1);

        return "kkk";
    }

    private void excute(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("select * from at_user");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
