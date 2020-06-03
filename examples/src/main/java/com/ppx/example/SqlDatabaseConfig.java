package com.ppx.example;

import java.util.HashMap;
import java.util.Map;

public class SqlDatabaseConfig {
    public static Map<String,String> mssqlParamMap=new HashMap<>();
    public static Map<String,String> oracleParamMap=new HashMap<>();
    public static Map<String,String> damengParamMap=new HashMap<>();

    public static String header;

    public static String footer;

    public static String sysservice;

    public static String sysparameter;

    static {
        header = "header";
        footer = "footer";
        sysservice = "SYSSERVICE";
        sysparameter = "SYSPARAMETER";

        mssqlParamMap.put(header, "IF NOT EXISTS (SELECT 1 FROM SYSSERVICE WHERE ID='$id$')\n" +
                "BEGIN");
        mssqlParamMap.put(footer, "END\n" +
                "go");
        mssqlParamMap.put(sysservice, "    insert into SYSSERVICE (id, code, caption, description, stype, requesturl, createtime, updatetime, creator, requestway, system, groupid) values " +
                "('$id$', '$apiCode$', '$apiCaption$', '$apiDesc$', $apiType$, " +
                "'$apiUrl$',  getdate(), getdate(), 'scott', 2, 1, '1210');\n");
        mssqlParamMap.put(sysparameter, "insert into SYSPARAMETER (id, xh, parametername, parameterposition, defaultvalue, parametertype, description) values " +
                "('$id$', $xh$, '$pName$', $pPosition$, $pDefaultValue$, '$pType$', '$pDesc$');\n");

    }

}
