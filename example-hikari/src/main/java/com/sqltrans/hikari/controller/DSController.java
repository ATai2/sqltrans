package com.sqltrans.hikari.controller;

import com.sqltrans.hikari.utils.BeanRegisterUtils;
import com.sqltrans.hikari.utils.SpringBeanTools;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.swing.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class DSController {


    @Autowired
    private DataSource dataSource;

    @Autowired
    CollectorRegistry collectorRegistry;

//    private CollectorRegistry collectorRegistry = CollectorRegistry.defaultRegistry;
    private PrometheusMetricsTrackerFactory factory = new PrometheusMetricsTrackerFactory(collectorRegistry);


    @GetMapping(value = "/m",produces = {"text/plain; version=0.0.4; charset=utf-8"})
    public String eme() throws IOException {
        Writer writer = new StringWriter();
        TextFormat.write004(writer, this.collectorRegistry.metricFamilySamples());
        return writer.toString();
    }


    @GetMapping("/ds")
    public String addDataSource() {


        try {
            String hikariDataSource1 = "hikariDataSource1";
            Object bean = SpringBeanTools.getBean(hikariDataSource1);
            if (bean == null) {
                BeanRegisterUtils.removeBean((ConfigurableApplicationContext) SpringBeanTools.getApplicationContext(), hikariDataSource1);

            }
            String hikariDataSource2 = "hikariDataSource2";
            Object bean2 = SpringBeanTools.getBean(hikariDataSource2);
            if (bean == null) {
                BeanRegisterUtils.removeBean((ConfigurableApplicationContext) SpringBeanTools.getApplicationContext(), hikariDataSource2);

            }
        } catch (Exception e) {
            System.out.println("No bean");
        }


        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mysql");
        config.setPassword("root");
        config.setPoolName("test1");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        BeanRegisterUtils.registerBean("hikariDataSource", HikariDataSource.class, config);
        config.setMetricsTrackerFactory(factory);

        HikariDataSource test = (HikariDataSource) SpringBeanTools.getBean("hikariDataSource");
        System.out.println(test);
        HikariConfig config1 = new HikariConfig();
        config1.setUsername("root");
        config1.setJdbcUrl("jdbc:mysql://94.191.68.209:13306/mall");
        config1.setPassword("root");
        config1.setDriverClassName("com.mysql.jdbc.Driver");
        config1.setPoolName("hikari2");
        config1.setPoolName("test2");
        config1.setMetricsTrackerFactory(factory);
        BeanRegisterUtils.registerBean("hikariDataSource2", HikariDataSource.class, config1);

        HikariDataSource test1 = (HikariDataSource) SpringBeanTools.getBean("hikariDataSource2");
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
