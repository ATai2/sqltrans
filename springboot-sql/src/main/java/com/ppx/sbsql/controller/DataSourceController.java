package com.ppx.sbsql.controller;

import com.ppx.sbsql.entity.DataSourceDTO;
import com.ppx.sbsql.utils.Result;
import com.ppx.sqltrans.databases.ConnConfig;
import com.ppx.sqltrans.databases.Database;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class DataSourceController {


    @GetMapping("hello")
    public Result hello() throws UnsupportedEncodingException {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        ApplicationHome  home=new ApplicationHome(getClass());
        File source = home.getSource();
        return Result.withSuccess(decodedPath+"\n"+source.getParent().toString());
    }
    @GetMapping("database")
    public Result getDatabase() {
        return Result.withSuccess(null);
    }

    @GetMapping("tables")
    public Result getTables(DataSourceDTO dataSourceDTO) {
        ConnConfig connConfig = new ConnConfig();
        connConfig.setDriverClass(dataSourceDTO.getDriver());
        connConfig.setJdbcUrl(dataSourceDTO.getUrl());
        connConfig.setUserName(dataSourceDTO.getName());
        connConfig.setPassword(dataSourceDTO.getPwd());
        Database database = Database.getInstance(connConfig);
        List<String> table_name=null;
        try {
            List<Map<String, Object>> tables = database.getTables(new String[]{"TABLE"});
            table_name = tables.stream().map(new Function<Map<String, Object>, String>() {
                @Override
                public String apply(Map<String, Object> map) {
                    return (String) map.get("TABLE_NAME");
                }
            }).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Result.withSuccess(table_name);
    }

    private Connection getConnection() {
        Connection c = null;
        try {
            String dbPath = "";
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

}
