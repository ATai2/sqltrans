package com.ppx.sqltrans.core;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.SQLParser;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;


public class MainTest {

    @Test
    public void main() {
        String str = "create  database IF NOT EXISTS hncu CHARACTER SET utf8;" +
                "create database ssss;" +
                " ";
        String dbType = JdbcConstants.MYSQL;
        SQLParser sqlParser=new SQLParser(str);
        String format = SQLUtils.format(str, dbType);
        System.out.println(format);

//        切换语法
        String formatSQLServer = SQLUtils.formatSQLServer(str);
        System.out.println(formatSQLServer);

    }

    @Test
    public void test_Oracle_to_mysql(){
        String oracle="";

    }
}