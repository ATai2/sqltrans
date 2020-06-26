package com.sqltrans.hikari.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class DatasourceUtils {

    public static ConcurrentHashMap<String,DataSource> dataSourceConcurrentHashMap=new ConcurrentHashMap<>();
    private static String defaultPoolName="default";

    /**
     *  通过配置信息，获得的datasource
     * @param config
     * @return
     */
    public static DataSource getDataSource(HikariConfig config) {
        String poolName = config.getPoolName();
        if (StringUtils.isEmpty(poolName)) {
            poolName = defaultPoolName;
        }
        DataSource dataSource = dataSourceConcurrentHashMap.get(poolName);
        if (dataSource != null) {
            return dataSource;
        }
        return new HikariDataSource(config);
    }


    public Connection getConnection() {

        DataSource dataSource = dataSourceConcurrentHashMap.get(defaultPoolName);
        try {
           return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("can't obtain connection from "+defaultPoolName);
        }
    }




}
