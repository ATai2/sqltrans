package com.ppx.sqltrans.databases.pagequery.fuzzyquery;

public interface IFuzzyQuery {

    /**
     * 模糊查询
     * @param filed
     * @param value
     * @return
     */
    public String like(String filed, String value);

}
