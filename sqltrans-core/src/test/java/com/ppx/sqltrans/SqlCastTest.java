package com.ppx.sqltrans;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.support.json.JSONUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlCastTest {

    @Test
    public void mysql2Oracle() {
    }

    @Test
    public void showDatabases() {
        String mysqlStr = "show databases;";
        System.out.println(SQLUtils.formatMySql(mysqlStr));
//        SQLUtils.parseStatements(mysqlStr, );
//        SQLExpr sqlExpr = SQLUtils.toMySqlExpr(mysqlStr);
//
//        System.out.println(JSONUtils.toJSONString(sqlExpr));

    }
}