package com.ppx.sqltrans.databases.pagequery;


import com.ppx.sqltrans.databases.OrderByOrder;

public class MSsqlPageQuery implements IPageQuery {

    private static String template = "select * from (" +
            "select row_number() over(order by %orderName% %order%) ROWNUMBER," +
            "%queryclause%" +
            ") T where T.ROWNUMBER >  %start% and T.ROWNUMBER < %end%";

    @Override
    public String pageQuery(String sql, int start, int end, String orderName, OrderByOrder orderByOrder) {

        sql = sql.replace("select", "");
        return template.replace("%queryclause%", sql)
                .replace("%orderName%", orderName)
                .replace("%order%", orderByOrder.getOrder())
                .replace("%start%", start + "")
                .replace("%end%", "" + end);
    }
}
