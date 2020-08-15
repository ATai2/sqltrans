package com.ppx.sqltrans.databases.pagequery.fuzzyquery;

public class BiFuzzyQuery implements IFuzzyQuery {

    @Override
    public String like(String filed, String value) {
        // LOWER(caption) LIKE LOWER(CONCAT(CONCAT('%',:ETLPROCESS_CREATOR),'%')) ESCAPE '/'


        return null;
    }
}
