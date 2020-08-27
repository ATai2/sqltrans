package com.ppx.sqltrans.databases.pagequery;


import com.ppx.sqltrans.databases.OrderByOrder;

public interface IPageQuery {

    public String pageQuery(String sql, int start, int end, String orderName, OrderByOrder orderByOrder);
}
