package com.sqltrans.hikari.com.sqltrans.hikari.controller;

import com.sqltrans.hikari.utils.BeanRegisterUtils;
import com.sqltrans.hikari.utils.SpringBeanTools;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DSController {

    @GetMapping("/ds")
    public String addDataSource(){
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



        return "kkk";
    }
}
