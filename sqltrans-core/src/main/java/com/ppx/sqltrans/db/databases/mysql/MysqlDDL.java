package com.ppx.sqltrans.db.databases.mysql;

import com.ppx.sqltrans.db.databases.DDLStatement;

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
