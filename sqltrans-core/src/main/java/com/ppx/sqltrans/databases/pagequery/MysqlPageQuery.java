package com.ppx.sqltrans.databases.pagequery;

import com.inspur.dmp.dbaccess.OrderByOrder;

public class MysqlPageQuery implements IPageQuery {

    private static String template = " %queryclause%   order by %orderName% %order%  limit %start% , %size% ";

    @Override
    public String pageQuery(String sql, int start, int end, String orderName, OrderByOrder orderByOrder) {
        return template.replace("%queryclause%", sql)
                .replace("%orderName%", orderName)
                .replace("%order%", orderByOrder.getOrder())
                .replace("%start%", start + "")
                .replace("%size%", "" + (end - start));
    }
}
