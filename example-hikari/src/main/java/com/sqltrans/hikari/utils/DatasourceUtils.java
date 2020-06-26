package com.sqltrans.hikari.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatasourceUtils {

    public static DataSource getDataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }




}
