package com.ppx.sqltrans.databases.pagequery.fuzzyquery;

public class AbstractQuery {

    static String MSSQL_SERVER_BI="lower($field$) LIKE lower(CONCAT(CONCAT('%',?,'%')) ESCAPE '/'";
    static String MSSQL_SERVER_LEFT="lower($field$) LIKE lower(CONCAT(CONCAT('%',?,'%')) ESCAPE '/'";

    private String sql;

    public String convert2sql(){
        return sql;
    }



}
