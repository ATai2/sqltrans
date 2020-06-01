package com.ppx.sqltrans.databases.mysql;

import com.ppx.sqltrans.databases.DDLStatement;

public class MysqlDDL implements DDLStatement {


    @Override
    public String getCreateDatabase() {
        return "";
    }

    @Override
    public String getDatabases() {
        return "show databases;";
    }
}
