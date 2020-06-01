package com.ppx.sqltrans.databases.oracle;

import com.ppx.sqltrans.databases.DDLStatement;

public class OracleDDL implements DDLStatement {
    @Override
    public String getCreateDatabase() {
        return null;
    }

    @Override
    public String getDatabases() {
        return " select name from SysDatabases order by name   ";
    }
}
