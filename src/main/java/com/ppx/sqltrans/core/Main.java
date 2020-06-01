package com.ppx.sqltrans.core;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLParser;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String str = "create  database IF NOT EXISTS hncu CHARACTER SET utf8;" +
                "create database ssss;" +
                " ";
        String dbType = JdbcConstants.MYSQL;
        SQLParser sqlParser=new SQLParser(str);

        String format = SQLUtils.format(str, dbType);
        System.out.println(format);

        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(str, dbType);
        int a=0;
        for (int i = 0; i < sqlStatements.size(); i++) {
            SQLStatement sqlStatement = sqlStatements.get(i);
            MySqlSchemaStatVisitor visitor=new MySqlSchemaStatVisitor();
            sqlStatement.accept(visitor);
            //获取表名称
//            System.out.println("Tables : " + visitor.getCurrentTable());
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
        }
    }
}
