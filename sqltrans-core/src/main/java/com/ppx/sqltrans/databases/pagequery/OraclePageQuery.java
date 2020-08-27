package com.ppx.sqltrans.databases.pagequery;


import com.ppx.sqltrans.DmpException;
import com.ppx.sqltrans.ErrorCodeEnum;
import com.ppx.sqltrans.databases.OrderByOrder;
import org.apache.commons.lang3.StringUtils;

/**
 * oracle dialect
 * rowno从1开始
 */
public class OraclePageQuery implements IPageQuery {

    private static String template = "select TT.* from (" +
            "SELECT ROWNUM AS rowno, t.*  FROM(" +
            "%queryclause%" +" order by %orderName% %order%"+
            " ) T " +
            ")TT where TT.ROWNO > %start% and TT.ROWNO < %end%";

    @Override
    public String pageQuery(String sql, int start, int end, String orderName, OrderByOrder orderByOrder) {
        if (StringUtils.isEmpty(sql)) {
            throw new DmpException(ErrorCodeEnum.DB_QUERYCLAUSE_NOT_EMPTY);
        }
        return template.replace("%queryclause%", sql)
                .replace("%orderName%", orderName)
                .replace("%order%", orderByOrder.getOrder())
                .replace("%start%", start + "")
                .replace("%end%", "" + end);
    }
}
