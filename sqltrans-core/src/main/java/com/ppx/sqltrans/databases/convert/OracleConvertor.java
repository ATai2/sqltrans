package com.ppx.sqltrans.databases.convert;

public class OracleConvertor extends SqlConvertor {

    static String BILIKE = " lower($field$) LIKE lower(CONCAT(CONCAT('%',?),'%')) ESCAPE '/' ";
    static String DATE_FORMAT = "TO_CHAR($$,'yyyy-MM-dd HH24:mi:ss')";


    @Override
    public String getBilike() {
        return BILIKE;
    }

    @Override
    public String getBilikeValue(String origin) {
        return origin.replace("_", "/_");
    }

    @Override
    public String getDateFormat() {
        return DATE_FORMAT;
    }

}
